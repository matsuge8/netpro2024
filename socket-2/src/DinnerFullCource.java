public class DinnerFullCource {
    private Dish[] list = new Dish[5];// [0]-[4]の計5個

	public static void main(String[] args) {

		DinnerFullCource fullcourse = new DinnerFullCource();
		fullcourse.eatAll();
	}

    DinnerFullCource(){
		list[0] = new Dish();
		list[0].setName("前菜：サラダ");
		list[0].setValune(10);
		list[1] = new Dish();
		list[1].setName("スープ：コーンスープ");
		list[1].setValune(15);
		list[2] = new Dish();
		list[2].setName("魚料理：鯛の煮つけ");
		list[2].setValune(20);
		list[3] = new Dish();
		list[3].setName("肉料理：ステーキ");
		list[3].setValune(20);
		list[4] = new Dish();
		list[4].setName("デザート：ケーキ");
		list[4].setValune(15);

        
    }
	void eatAll(){
		String s = "";
		for(Dish a : list){
			s += a.getName() + "値段は" + a.getValune() +"。次は";
		}

		System.out.println(s);
	}

}
