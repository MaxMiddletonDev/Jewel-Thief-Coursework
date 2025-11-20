package cs230.group29se.jewelthief;

public class TestingLootEnum {
    public static void main (String[] args){
        LootEnum coin = LootEnum.DOLLAR;
        System.out.println(coin.getValue());
        System.out.println(coin.name());

        int[] aPosition = new int[] {1,2};
        Loot dollar = new Loot(aPosition,LootEnum.DOLLAR);

        System.out.println(dollar.getValue());
        System.out.println(dollar.getType());

        if (dollar instanceof Item) {
            System.out.println("totally");
        }

        Bomb bomb = new Bomb();

        bomb.interact();
    }
}