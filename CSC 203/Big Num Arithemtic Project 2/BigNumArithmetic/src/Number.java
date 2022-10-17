public class Number {
    private LinkedList numList = new LinkedList();

    public Number() throws NumberException{ //set the numList to 0, ., 0
        this("0.0"); //(delegate constructor)
    }

    public Number(String value) throws NumberException{
        int decCount = 0; //will count number of decimals in the number

        for(int i = 0; i < value.length(); i++) {

            switch (value.charAt(i)){

                case '.':
                    decCount++;
                    if(decCount > 1) { //ERROR
                        throw new NumberException("Multiple Decimals");
                    } else{
                        try {
                            numList.addToEnd(new Node(value.charAt(i)));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case '-':
                    if(i > 0){ //a '-' anywhere after the 0th index throws an exception
                        throw new NumberException("Invalid Character");
                    }
                    else{
                        try {
                            numList.addToEnd(new Node(value.charAt(i)));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                default:
                    if (!Character.isDigit(value.charAt(i))) { //every other character should only be a digit
                        throw new NumberException("Invalid Character");
                    } else {
                        try {
                            numList.addToEnd(new Node(value.charAt(i)));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
            }
        }
        try {
            if(decCount == 0){ //if there are no decimals, add a ".0"
                numList.addToEnd(new Node('.'));
                numList.addToEnd(new Node('0'));
            }
            if(value.charAt(value.length() - 1) == '.'){ //if the number ends with just a decimal, add a 0
                numList.addToEnd(new Node('0'));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public LinkedList getNumList() { return this.numList; }

    public Number add(Number n){
        int carry = 0;
        int addOn = 0;
        String rv = "";
        String smallLeftStr; //before decimal
        String smallRightStr; //after decimal
        int indexDecThis = this.toString().indexOf(".");
        int indexDecBd = n.toString().indexOf(".");
        Number sum = null;

        //padding left side of decimal
        smallLeftStr = (indexDecThis > indexDecBd) ? this.toString() : n.toString(); //smallLeftStr is set to the smaller left side
        int differenceInLengthLeft = (indexDecThis > indexDecBd) ? indexDecBd - indexDecThis : indexDecThis - indexDecBd;
        smallLeftStr = this.paddingZeros(smallLeftStr, differenceInLengthLeft, true); //pad the number with the smaller left side with leading zeros

        try {
            if (indexDecThis > indexDecBd){
                setValue(smallLeftStr);
            }
            else{
                n.setValue(smallLeftStr);
            }
        } catch (NumberException nE) {
            System.out.println(nE.getMessage());
        }

        //padding right side of decimal
        smallRightStr = (this.toString().length() - this.toString().indexOf(".")) < (n.toString().length() - n.toString().indexOf(".")) ? this.toString() : n.toString(); //smallRightStr is set to the smaller right side
        int differenceInLengthRight = (indexDecThis > indexDecBd) ? indexDecBd - indexDecThis : indexDecThis - indexDecBd;
        smallRightStr = paddingZeros(smallRightStr, differenceInLengthRight, false); //pad the number with the smaller right side with leading zeros

        try {
            if ((this.toString().length() - this.toString().indexOf(".")) < (n.toString().length() - n.toString().indexOf("."))) {
                setValue(smallRightStr);
            } else {
                n.setValue(smallRightStr);
            }
        } catch (NumberException nE) {
            System.out.println(nE.getMessage());
        }
        //addition of the two BigDecimals
        for(int i = this.toString().length() - 1; i >= 0; i--){
            if(this.toString().charAt(i) == '.'){ //found a decimal in the numbers, this should also go in the answer at the same place
                rv = "." + rv;
            }
            else{
                addOn = Character.getNumericValue(this.toString().charAt(i)) + Character.getNumericValue(n.toString().charAt(i)) + carry;
                if(addOn >= 10 && i>0 ){ //not the first digit
                    addOn %= 10; //greater than 10, so we need a carry of 1
                    carry = 1;
                }
                else{
                    carry = 0;
                }
                rv = "" + addOn + rv; //final string value of the sum
            }
        }
        try{
            sum = new Number(rv); //make the Number from the resulting string
        } catch(NumberException nE){
            System.out.println(nE.getMessage());
        }
        return sum;
    }
    public Number exponent(Number n) throws NumberException{
        Node p = n.getNumList().getHead();
        int expNum = (int) n.toDouble();
        double baseNum = this.toDouble();
        double answer;
        while(p.getNext().getData() != '.') {
            p = p.getNext();
        }
        if(expNum % 2 ==0) {
            answer = Math.pow(Math.pow(baseNum, 2), expNum/2);
        } else {
            answer = baseNum * Math.pow(Math.pow(baseNum, 2), (expNum - 1) / 2);
        }
        return new Number(answer + "");
    }
    public double toDouble() {
        Node p = this.numList.getTail();
        String stringOfNum = "";
        while (p.getPrev() != null) {
            stringOfNum = p.getData() + stringOfNum;
            p = p.getPrev();
        }
        return Double.parseDouble(stringOfNum);
    }
    public String toString(){
        String rv = "";
        for(int i = 0; i < this.numList.size(); i++){
            rv += "" + this.numList.getNodeAt(i);
        }
        return rv;
    }
    public String paddingZeros(String input, int numZeros, boolean padInFront){
        String paddedStr = input;
        if(padInFront){ //add leading zeros
            for (int i = 0; i < numZeros; i++) {
                paddedStr = "0" + paddedStr;
            }
        }
        else{ //add trailing zeros
            for (int i = 0; i < numZeros; i++) {
                paddedStr += "0";
            }
        }
        return paddedStr;
    }

    public void setValue(char ch) throws NumberException{
        if(!Character.isDigit(ch) && ch != '.'){ //if data section is being set to anything that isn't a digit or "-"
            throw new NumberException("Invalid Character");
        }
        else{
            numList.clear();
            try {
                numList.addToEnd(new Node(ch));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void setValue(String value) throws NumberException{
        int countDec = 0;
        numList.clear();
        for(int i = 0; i < value.length(); i++){
            switch(value.charAt(i)){
                case '.':
                    countDec++;
                    if(countDec > 1){ //if there are multiple decimals, throw exception
                        throw new NumberException("Multiple Decimals");
                    }
                    else{
                        try {
                            numList.addToEnd(new Node(value.charAt(i)));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case '-':
                    if(i > 0){ //a '-' anywhere after the 0th index throws an exception
                        throw new NumberException("Invalid Character");
                    }
                    else{
                        try {
                            numList.addToEnd(new Node(value.charAt(i)));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                default:
                    if(!Character.isDigit(value.charAt(i))){ //every other character should only be a digit
                        throw new NumberException("Invalid Character");
                    }
                    else{
                        try {
                            numList.addToEnd(new Node(value.charAt(i)));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
            }
        }
    }
}
