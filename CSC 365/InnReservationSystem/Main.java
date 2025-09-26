#def handle_reservations():
    """
    FR2: Reservations
    This requires a bunch of user input, searching for available rooms, etc.
    Let's hope user doesn't type nonsense...
    """
    try:
        fname = input("First Name: ").strip()
        lname = input("Last Name: ").strip()
        rm_pref = input("Room Code (or 'Any'): ").strip()
        bed_pref = input("Bed Type (or 'Any'): ").strip()
        start_date = input("Start Date (YYYY-MM-DD): ").strip()
        end_date = input("End Date (YYYY-MM-DD): ").strip()
        adults = int(input("Number of Adults: "))
        kids = int(input("Number of Children: "))
        guests = adults + kids

        # Validate date input, just a tiny bit :D :
        try:
            start_dt = datetime.strptime(start_date, "%Y-%m-%d")
            end_dt = datetime.strptime(end_date, "%Y-%m-%d")
            if end_dt <= start_dt:
                print("End date must be after start date.")
                return
        except ValueError:
            print("Date format error, please use YYYY-MM-DD")
            return

        # Construct query
        # We'll use parameterized queries to avoid injection.
        # If 'Any', we won't add that filter
        params = []
        query_filters = ["r.maxOcc >= %s"]
        params.append(guests)

        if rm_pref.lower() != "any":
            query_filters.append("r.RoomCode = %s")
            params.append(rm_pref)

        if bed_pref.lower() != "any":
            query_filters.append("r.bedType = %s")
            params.append(bed_pref)

        # For availability: no overlapping reservation
        # We'll just ensure no reservation overlaps the given date range
        # i.e. NOT EXISTS a reservation with overlapping date range
        q_base = f"""
            SELECT r.RoomCode, r.RoomName, r.bedType, r.basePrice, r.maxOcc
            FROM lab7_rooms r
            WHERE {" AND ".join(query_filters)}
              AND NOT EXISTS (
                  SELECT 1 FROM lab7_reservations res
                  WHERE res.Room = r.RoomCode
                    AND res.CheckIn < %s
                    AND res.Checkout > %s
              )
        """
        # Add the date params at the end
        #params.append(datetime(end_date))
        #params.append(end_dt)
        #params.append(datetime(start_date))
        #params.append(start_dt)

        params.append(end_dt.strftime("%Y-%m-%d"))
        params.append(start_dt.strftime("%Y-%m-%d"))

        df = pd.read_sql_query(q_base, conn, params=params)

        if df.empty:
            # No exact match. Suggest alternatives
            print("No exact matches found. Let's suggest some alternatives:")
            alt_query = """
                SELECT r.RoomCode, r.RoomName, r.bedType, r.basePrice, r.maxOcc
                FROM lab7_rooms r
                WHERE r.maxOcc >= %s
                  AND NOT EXISTS (
                      SELECT 1 FROM lab7_reservations rr
                      WHERE rr.Room = r.RoomCode
                        AND rr.CheckIn < DATE_ADD(%s, INTERVAL 1 DAY)
                        AND rr.Checkout > DATE_SUB(%s, INTERVAL 1 DAY)
                  )
                LIMIT 5
            """
            alt_suggestions = pd.read_sql(alt_query, conn, params=(guests, end_date, start_date))
            if alt_suggestions.empty:
                print("No suitable rooms found at all. Sorry!")
                return
            else:
                print("Possible Suggestions:")
                print(alt_suggestions.to_string(index=False))
                chosen_code = input("Type a RoomCode from these suggestions to book, or 'cancel': ").strip()
                if chosen_code.lower() == 'cancel':
                    print("Reservation request aborted.")
                    return
                chosen_room = alt_suggestions[alt_suggestions['RoomCode'] == chosen_code]
                if chosen_room.empty:
                    print("Invalid choice.")
                    return
                else:
                    insert_reservation(fname, lname, chosen_code,
                                       chosen_room['RoomName'].iloc[0],
                                       chosen_room['bedType'].iloc[0],
                                       start_date, end_date, adults, kids,
                                       chosen_room['basePrice'].iloc[0])
        else:
            print("Available Rooms:")
            print(df.to_string(index=False))
            chosen_code = input("Enter the RoomCode to book, or 'cancel': ").strip()
            if chosen_code.lower() == "cancel":
                print("Canceled.")
                return
            chosen_df = df[df['RoomCode'] == chosen_code]
            if chosen_df.empty:
                print("No room with that code from the list.")
                return
            insert_reservation(fname, lname, chosen_code,
                               chosen_df['RoomName'].iloc[0],
                               chosen_df['bedType'].iloc[0],
                               start_date, end_date, adults, kids,
                               chosen_df['basePrice'].iloc[0])

    except Exception as e:
        print("Oops, something went wrong in reservations.")
        print(e)