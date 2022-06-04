package test.buildings;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.buildings.HerosCastleBuilding;

public class HerosCastleBuildingTest {

    @Test
    public void testSpawnEnemy(){

      HerosCastleBuilding building = new HerosCastleBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
      
      // TODO: Test effect on character position

    }
    
}
