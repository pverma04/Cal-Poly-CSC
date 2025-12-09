CryptoTracker

CryptoTracker is a native Android app built with Kotlin and 
Jetpack Compose that displays real-time cryptocurrency market data. 
The app fetches live prices, 24-hour performance, market cap, volume, 
and other metrics from the CoinGecko API and presents them in an 
interactive, modern UI. 
Users can browse the top cryptocurrencies, 
tap any coin to view detailed statistics, and refresh market data on demand.

NOTE: FIGMA SCREENS ARE LOCATED IN HIGHEST LEVEL FOLDER
CSC436/CryptoTracker/FigmaScreen1.png
CSC436/CryptoTracker/FigmaScreen2.png


_**Android & Jetpack Compose Features Used:**_
**_Core Jetpack Compose Components:_**
- Scaffold, TopAppBar, LazyColumn, Row, Column, Text, Divider 
- Material 3 components 
- State handling with mutableStateOf and recomposition 
- viewModel() integration with Compose 
- Navigation via screen state (manual screen switching)

**_Architecture_**
- MVVM Pattern
  - CoinListViewModel: state management & API orchestration 
  - UI screens observe ViewModel state and recompose automatically
- data class models for clean mapping from JSON → UI

**_Networking & Concurrency_**
- HttpURLConnection for REST API calls (no Retrofit required)
- Kotlin Coroutines
  - Dispatchers.IO for background work 
  - viewModelScope.launch for structured concurrency 
- JSON parsing with JSONArray / JSONObject

**_Image Loading (3rd Party Library)_**
- Coil (coil-compose)
  - Displays live coin images 
  - Handles caching, async loading, and scaling

**_Formatting Utilities_**
- Number formatting (NumberFormat) for currency 
- Custom big-number formatter (K, M, B, T formatting)
- Dynamic color styling (green/red) for 24h % change

**_Device / SDK Requirements_**
- Minimum SDK: 24
- Internet Permission Required
NOTE: ADD IN TO AndroidManifest.xml:
<uses-permission android:name="android.permission.INTERNET" />
- No special device hardware needed

**_Features_**
- Fetches top cryptocurrencies (sorted by market cap)
- Displays coin name, icon, symbol, current price, and 24h change
- Tap any coin → detailed view
- Detail screen shows:
- Price
- 24h change (green/red)
- 24h high/low
- Market cap (with K/M/B/T format)
- Total volume
- Coin icon (via Coil)
- Refresh button reloads market data
- Graceful error handling and rate-limit recovery
- Maintains previous successful data if API call fails

**_Above and Beyond_**
- Added professional-grade image loading using Coil, not covered in class
- Implemented custom large-number formatting (12.4B, 5.1M, etc.)
- Added auto-recompose architecture using Compose + ViewModel
- Built a two-screen navigation flow (List → Detail → Back) without any navigation libraries
- Styled dynamic UI elements using conditional coloring
- Handled API failures gracefully by preserving previous UI state
- Designed and implemented a clean, expandable MVVM structure