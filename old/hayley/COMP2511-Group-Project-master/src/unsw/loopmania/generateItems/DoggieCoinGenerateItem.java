package unsw.loopmania.generateItems;

import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.items.DoggieCoin;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;

import java.util.Random;

public class DoggieCoinGenerateItem extends GenerateItem {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;
    private int upperValue;
    private Random rand = new Random(System.currentTimeMillis());

    public DoggieCoinGenerateItem() {
        this.name = new SimpleStringProperty("DoggieCoin");
        this.description = new SimpleStringProperty("A revolutionary asset type, which randomly fluctuates in sellable price to an extraordinary extent. Can sell at shop.");
        this.upperValue = 10000;
        this.price = new SimpleIntegerProperty(1);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
        @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                       randPrice();
                    }
                });
            }
        }, 0, 1000);
        setType(ItemType.NOT_EQUIPPABLE);
    }

    public Item createItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Item newDoggieCoin = new DoggieCoin(x, y);
        return newDoggieCoin;
    }

    public void setUpperValue(int x) {
        upperValue = x;
    }

    public int getUpperValue() {
        return upperValue;
    }

    private void randPrice() {
        this.price.set(rand.nextInt(upperValue));
    }

    public SimpleStringProperty description() {
        return description;
    }

    public SimpleStringProperty name() {
        return name;
    }

    public SimpleIntegerProperty price() {
        return price;
    }
}
