package sample.logic;

public enum Movement {
    UP(-1),
    DOWN(+1),
    LEFT(-1),
    RIGHT(+1),
    NONE(0);

//    WHITE_UP(-1, 0),
//    WHITE_LEFT(0, -1),
//    WHITE_RIGHT(0, +1),
//    WHITE_DOWN(+1, 0),
//
//    BLACK_UP(-1, 0),
//    BLACK_LEFT(0, -1),
//    BLACK_RIGHT(0, +1),
//    BLACK_DOWN(+1, 0),
//
//    UP_RIGHT(-1, +1),
//    UP_LEFT(-1, -1),
//
//    DOWN_RIGHT(+1, +1),
//    DOWN_LEFT(+1, -1);


    // declaring private variable for getting values
    private int action;

    public int getAction(){
        return action;
    }

    private Movement(int action, int action1){

    }

    private Movement(int action){
        this.action = action;
    }


}
