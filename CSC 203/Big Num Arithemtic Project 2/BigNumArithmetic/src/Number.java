public class Number {
    private LinkedList numList = new LinkedList();
    final int RADIX = 10;

    public Number() throws NumberException{ //set the numList to 0, ., 0
        this(""); //(delegate constructor)
    }
    public Number(String value) throws NumberException {
        for(int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                throw new NumberException("Invalid Character");
            } else {
                try {
                    numList.addToEnd(new Node(value.charAt(i)));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public LinkedList getNumList() { return this.numList; }


    public Number add(Number n) throws NumberException {
        int carry = 0;
        int addOn;
        int digit;
        Number sum = new Number();
        //pad left side of shorter Number with Zeros
        Number smallerNum = this.numList.size() >= n.numList.size() ? n : this;
        Number biggerNum = this.numList.size() >= n.numList.size() ? this : n;
        smallerNum = this.addZeros(smallerNum, biggerNum.numList.size() - smallerNum.numList.size(), true);

        //addition of the two BigDecimals
        Node smallerNumTail = smallerNum.getNumList().getTail();
        Node biggerNumTail = biggerNum.getNumList().getTail();
        while (smallerNumTail != null && biggerNumTail != null) {
            addOn = carry + Character.getNumericValue(smallerNumTail.getData()) + Character.getNumericValue(biggerNumTail.getData());
            digit = addOn % 10;
            carry = addOn / 10;
            sum.numList.addToFront(new Node(digit));
            smallerNumTail = smallerNumTail.getPrev();
            biggerNumTail = biggerNumTail.getPrev();
        }
        if (carry != 0) { //if carry is a non-zero number, then it must be included at the front of the number
            sum.numList.addToFront(new Node(carry));
        }
        return sum;
    }



    public Number multiply(Number n) throws NumberException {
        Number bigger = this.numList.size() > n.getNumList().size() ? this : n;
        Number smaller = this.numList.size() < n.getNumList().size() ? this : n;
        Number finalProduct = new Number();
        Number intermediateProduct = new Number();

        int iCurrentMultiplier = 0;
        int iCurrentMultiplicant;
        int iCurrentProduct = 0;
        int carry = 0;
        int newDigit = 0;
        int indexIntermediate = 0;
        Node smallerTail = smaller.numList.getTail();
        Node biggerTail = bigger.numList.getTail();

        while (smallerTail != null) {
            iCurrentMultiplier =  Character.getNumericValue(smallerTail.getData());
            intermediateProduct = this.multiplyByDigit(bigger, iCurrentMultiplier);
            intermediateProduct = this.addZeros(intermediateProduct, indexIntermediate, false);
            //System.out.println("Intermediate " + intermediateProduct.toString());
            //System.out.println("Final " + finalProduct.toString());
            finalProduct = finalProduct.add(intermediateProduct);
            //System.out.println("-- Final " + finalProduct.toString());
            smallerTail = smallerTail.getPrev();
            indexIntermediate++;
        }
        return finalProduct;
    }
    private Number addZeros(Number n, int numZeros, boolean blnPadFront) {
        if (!blnPadFront) {
            for (int i = 0; i < numZeros; i++) {
                n.numList.addToEnd(new Node(0));
            }
        } else {
            for (int i = 0; i < numZeros; i++) {
                n.numList.addToFront(new Node(0));
            }
        }
        return n;
    }
    private Number multiplyByDigit(Number n, int digit) throws NumberException {
        Node nTail = n.numList.getTail();
        int iCurrentMultiplicant = 0;
        int carry = 0;
        int iCurrentProduct = 0;
        int newDigit = 0;
        Number intermediateProduct = new Number();
        while (nTail != null) { //this will not add the last number to be carried
            iCurrentMultiplicant =  Character.getNumericValue(nTail.getData());
            iCurrentProduct = carry + (digit * iCurrentMultiplicant);
            newDigit = iCurrentProduct % 10;
            carry = iCurrentProduct / 10;
            intermediateProduct.numList.addToFront(new Node(newDigit));
            nTail = nTail.getPrev();
        }
        if (carry != 0) { //if carry is a non-zero number, then it must be included at the front of the number
            intermediateProduct.numList.addToFront(new Node(carry));
        }
        return intermediateProduct;
    }
    /*
    public Number ninesComplement(){
        String rv = "";
        Number nComp = null;
        for(int i = this.toString().length() - 1; i >= 0; i--){ //starting from the one's place of the number (BigDecimal)
            if(this.toString().charAt(i) == '.'){
                rv = "." + rv;
            }
            else{
                rv = (9 - Character.getNumericValue(this.toString().charAt(i))) + rv; //creating the nine's complement
            }
        }
        if(rv.length() > this.toString().length()){
            String rv1 = "";
            for (int i = rv.length() - 1; i >= 0; i--) {
                if (this.toString().charAt(i) == '.') {
                    rv1 = "." + rv1;
                } else { //repeating the process if needed (following the nine's complement rules)
                    rv1 = (9 - Character.getNumericValue(rv.charAt(i))) + rv1;
                }
            }
            rv = rv1;
        }
        try{
            nComp = new Number(rv);
        }
        catch (NumberException nE){
            System.out.println(nE.getMessage());
        }
        return nComp;
    }
     */
    /*
    public Number subtract(Number n){
        Number answer;
        String rv = "";
        String smallLeftStr; //before decimal
        String smallRightStr; //after decimal
        int indexDecThis = this.toString().indexOf(".");
        int indexDecN = n.toString().indexOf(".");

        //padding left side of decimal
        smallLeftStr = (indexDecThis >= indexDecN) ? n.toString() : this.toString(); //smallLeftStr is set to the smaller left side
        int differenceInLengthLeft = (indexDecThis >= indexDecN) ? indexDecThis - indexDecN : indexDecN - indexDecThis;
        smallLeftStr = paddingZeros(smallLeftStr, differenceInLengthLeft, true); //pad the number with the smaller left side with leading zeros

        try {
            if (indexDecThis >= indexDecN) {
                n.setValue(smallLeftStr);
            } else {
                this.setValue(smallLeftStr);
            }
        } catch (NumberException nE) {
            System.out.println(nE.getMessage());
        }
        String thisRightSide = this.toString().substring(indexDecThis + 1); //substring starting after the decimal point
        String nRightSide = n.toString().substring(indexDecN + 1); //substring starting after the decimal point

        //padding right side of decimal
        smallRightStr = thisRightSide.length() >= nRightSide.length() ? n.toString() : this.toString(); //smallRightStr is set to the smaller right side
        int differenceInLengthRight = (thisRightSide.length() >= nRightSide.length()) ? indexDecThis - indexDecN : indexDecN - indexDecThis;
        smallRightStr = paddingZeros(smallRightStr, differenceInLengthRight, false); //pad the number with the smaller right side with trailing zeros

        try {
            if (thisRightSide.length() >= nRightSide.length()) {
                n.setValue(smallRightStr);
            } else {
                this.setValue(smallRightStr);
            }
        } catch (NumberException nE) {
            System.out.println(nE.getMessage());
        }

        answer = this.add(n.ninesComplement());

        if(answer.toString().length() > this.toString().length()){
            String carry = "";
            for(int i = 0; i < (answer.toString().length() - answer.toString().indexOf(".") - 2); i++){
                carry = "0" + carry;
            }
            carry = "." + carry + 1;
            try{
                answer.setValue(answer.toString().substring(1)); //cut off the first digt from the answer (following the complement rules)
            }
            catch(NumberException nE){
                System.out.println(nE.getMessage());
            }
            try{
                answer = answer.add(new Number(carry));
            }
            catch(NumberException nE){
                System.out.println(nE.getMessage());
            }
        }
        else{
            try{
                answer.setValue(answer.ninesComplement().toString());
            }
            catch(NumberException nE){
                System.out.println(nE.getMessage());
            }
            if (answer.toDouble() != 0.0){
                try {
                    answer.setValue("-" + answer.toString());
                }
                catch (NumberException nE) {
                    System.out.println(nE.getMessage());
                }
            }
            else{
                try{
                    answer.setValue("0.0");
                }
                catch (NumberException nE) {
                    System.out.println(nE.getMessage());
                }
            }
        }
        return answer;
    }
     */


    public Number exponent(Number n) throws NumberException{
        //Node p = n.getNumList().getHead();
        int expNum = (int) n.toDouble();
        double baseNum = this.toDouble();
        //double answer;
        //Number rv = new Number();
        String answer;
        if(expNum % 2 ==0) {
            answer = Double.toString(Math.pow(Math.pow(baseNum, 2), expNum/2));
        } else {
            answer = Double.toString(baseNum * Math.pow(Math.pow(baseNum, 2), (expNum - 1) / 2));
        }
        //String temp = Double.toString(answer);
        return new Number(answer);
    }

    public double toDouble() {
        Node p = this.numList.getHead();
        String stringOfNum = "";
        while (p.getNext() != null) {
            stringOfNum += p.getData();
            p = p.getNext();
        }
        return Double.parseDouble(stringOfNum);
    }
    public String toString(){
        String rv = "";
        for(int i = 0; i < this.numList.size(); i++){
            rv += "" + this.numList.getNodeAt(i).getData();
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




    /*
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
     */

    /*
    public Number add(Number n){
        int carry = 0;
        int addOn;
        String rv = "";
        String smallLeftStr; //before decimal
        String smallRightStr; //after decimal
        int indexDecThis = this.toString().indexOf(".");
        int indexDecN = n.toString().indexOf(".");
        Number sum = null;

        //padding left side of decimal
        smallLeftStr = (indexDecThis >= indexDecN) ? n.toString() : this.toString(); //smallLeftStr is set to the smaller left side
        int differenceInLengthLeft = (indexDecThis >= indexDecN) ? indexDecThis - indexDecN : indexDecN - indexDecThis;
        smallLeftStr = this.paddingZeros(smallLeftStr, differenceInLengthLeft, true); //pad the number with the smaller left side with leading zeros

        try {
            if (indexDecThis >= indexDecN){
                n.setValue(smallLeftStr);
                indexDecN = n.toString().indexOf('.'); //after the zeros are padded, check for new decimal position
            }
            else{
                this.setValue(smallLeftStr);
                indexDecThis = this.toString().indexOf('.'); //after the zeros are padded, check for new decimal position
            }
        } catch (NumberException nE) {
            System.out.println(nE.getMessage());
        }
        String thisRightSide = this.toString().substring(indexDecThis + 1); //substring starting after the decimal point
        String nRightSide = n.toString().substring(indexDecN + 1); //substring starting after the decimal point
        //padding right side of decimal
        smallRightStr = thisRightSide.length() < nRightSide.length() ? this.toString() : n.toString(); //smallRightStr is set to the smaller right side
        int differenceInLengthRight = (thisRightSide.length() < nRightSide.length()) ? nRightSide.length() - thisRightSide.length() : thisRightSide.length() - nRightSide.length();
        smallRightStr = paddingZeros(smallRightStr, differenceInLengthRight, false); //pad the number with the smaller right side with leading zeros

        try {
            //if ((this.toString().length() - this.toString().indexOf(".")) < (n.toString().length() - n.toString().indexOf("."))) {
            if (thisRightSide.length() >= nRightSide.length()) {
                n.setValue(smallRightStr);
            } else {
                this.setValue(smallRightStr);
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
     */
    /*
    public Number multiply(Number n) throws NumberException {
        Number bigger = this.numList.size() > n.getNumList().size() ? this : n;
        Number smaller = this.numList.size() < n.getNumList().size() ? this : n;
        int carry = 0;
        int current = 0;
        Number product = new Number();
        Number intermediate = new Number();
        String biggerString = bigger.toString();
        String smallerString = smaller.toString();
        Number currentProduct = new Number();
        for(int i = smallerString.length() - 1; i >= 0; i--) {
            for (int j = biggerString.length() - 1; j >= 0; j--) {
                if(smallerString.charAt(i) != '.' && biggerString.charAt(j) != '.') {
                    current = carry + (Character.getNumericValue(smallerString.charAt(i)) * Character.getNumericValue(biggerString.charAt(j)));
                    currentProduct.setValue(current + "");
                    if(j == biggerString.length()) {
                        intermediate.add(currentProduct);
                    } else {

                    }

                    intermediate.numList.addToFront(new Node((char) (current % 10)));
                    carry = current / 10;
                }
            }
            intermediate.numList.addToEnd(new Node('0'));
            product = product.add(intermediate);
        }
        return product;
    }
     */
}
