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
        Number smallerNum;
        Number biggerNum;
        //pad left side of shorter Number with Zeros
        if(this.numList.size() >= n.numList.size()) {
            smallerNum = n;
            biggerNum = this;
        } else {
            smallerNum = this;
            biggerNum = n;
        }
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
        Number bigger = this.numList.size() >= n.getNumList().size() ? this : n;
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

    private Number exponentBySquaring(Number baseNumber, int expN) throws NumberException {
        if (expN == 0) { //anything to the power of 0 is 1
            return new Number("1");
        } else if (expN % 2 == 0) { //even exponent formula
            return exponentBySquaring(baseNumber.multiply(baseNumber), expN / 2);
        } else { //odd exponent formula
            return baseNumber.multiply(exponentBySquaring(baseNumber.multiply(baseNumber), (expN - 1) / 2));
        }
    }
    public Number exponent(Number n) throws NumberException {
        int expN = Integer.parseInt(n.toString());
        return this.exponentBySquaring(this, expN);
    }

    public String toString(){
        String rv = "";
        for(int i = 0; i < this.numList.size(); i++){
            rv += "" + this.numList.getNodeAt(i).getData();
        }
        return rv;
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
