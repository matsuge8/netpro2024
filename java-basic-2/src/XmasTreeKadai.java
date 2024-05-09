public class XmasTreeKadai {
    public static void main(String[] args) {
        int hight = 20;
        int mikihight = 7;
        String x = "*";
		for(int i = 0; i < hight; i++){
            for(int h = 0; h <=20-i; h++){
                if(h%2 == 0){
                    System.out.print(" ");
                }else{
                System.out.print("+");
                }
            }
            for(int j = 0; j <= i; j++){
                System.out.print(x);
                System.out.print(x);
            }
            for(int h = 0; h <=20-i; h++){
                if(h%2 == 1){
                    System.out.print(" ");
                }else{
                System.out.print("+");
                }
            }
            System.out.print("\n");
            
        }
         for(int i = 0; i < mikihight; i++){
            for(int k = 0; k < hight-2; k++){
                System.out.print(" ");
            }
            System.out.print("***");
            System.out.print("\n");
        }
	}


	}
    //https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Integer.html

