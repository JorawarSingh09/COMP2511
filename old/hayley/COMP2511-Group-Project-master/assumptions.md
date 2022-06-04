# Assumptions
### Starting state
- A player starts with 50 health and can deal 1 damage

### Map Selection
- If the player does not select a map, then a default map is selected.
- The player has a choice of 3 maps.

### Game Mode Selection
- If the player does not select a game mode, standard mode is selected as the default.

### Spawning Enemies
- Only 2 Slugs may be spawned at any one time, if the Character kills one of two spawned then another may spawn elsewhere in the path in the same cycle.

### Movement
- When the game is in play, the character moves at a rate of one tile per second

### Enemy radius
- Regardless of enemy, the Character will engage in battle with an enemy that is in an adjacent tile

### Buying and equipping items
- The Human Player will not be allowed to buy any items the character can’t afford
- When in the Hero's Castle, the Player can buy an unlimited amount of any item available as long as the Character has enough Gold.
- The character can only equip one item of each type at a time, a seperate equip slot is available for each type
    - These types include:
        - Weapon
        - Armour
        - Helmet
        - Shield
        - Accessory
- The character can have multiple of each item type in their inventory
- Items will be sold for the same price as they are bought, and can be sold directly after buying
- Any items that require specific circumstances to activate must be equipped in order for the Character to recieve their benefits
    - e.g. The One Ring can resurrect the Character upon death, to do this it must be equipped in the Accessory slot. Once activated it will be destroyed as it is one-use.

### Battle behaviour
- Order of attack: character → allied soldiers in order of age → enemy → enemy support troops in order of distance
- Our character and our allied soldiers will inflict damage on the same enemy until it dies, and then move on to the next
- Enemy characters inflict damage on allied soldiers first, and will only move onto the character after the allied soldiers die
- An allied soldier has 20 health and can do 8 damage
- When fighting a boss, the boss will appear seperate to other enemies and fight the character simultaneously with them.

### Buildings and cards
- The player can have a maximum of 6 building cards
- A tower has a shooting radius of 2, and deals 1 point of damage on an enemy character for every second that it is within this shooting radius
- Buildings that are on path tiles are activated when the character/enemy steps on the same tile
- A campfire has a battle radius of 2
- When a Card is discarded due to the max number of Cards being exceeded, the Character is awarded 5 XP and 5 Gold
- If a building already exists on a tile then another building cannot be placed on top of it

### Enemy Item Drops as percentage chance
| Type | Anduril | Armour | DoggieCoin | HealthPotion | Helmet | ReversePathPotion | Shield | Staff | Stake | Sword | TheOneRing | TreeStump |
|------|---------|--------|------------|--------------|--------|-------------------|--------|-------|-------|-------|------------|----------|
| Zombie | 0.05 | 0 | 0 | 0.2 | 0 | 0.02 | 0 | 0.5 | 0 | 0 | 0.05 | 0.05 |
| Vampire | 0.05 | 0 | 0 | 0.2 | 0 | 0.02 | 0.5 | 0 | 0.5 | 0 | 0.05 | 0.05 |
| Slug | 0 | 0 | 0 | 0.2 | 0 | 0 | 0 | 0 | 0 | 0.5 | 0.05 | 0.05 |
| Doggie | 0 | 0 | 1 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 |
| ElanMuske | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 

### Enemy Cards Drops as percentage chance
| Type | BarracksCard | CampfireCard | TowerCard | TrapCard | VampireCastleCard | VillageCard | ZombiePitCard |
|------|--------------|--------------|-----------|----------|-------------------|-------------|---------------|
| Slug | 0.30 | 0.05 | 0.05 | 0.05 | 0.3 | 0.05 | 0.3 |
| Vampire | 0.05 | 0.3 | 0.05 | 0.05 | 0.3 | 0.05 | 0 |
| Zombie | 0.05 | 0.05 | 0.3 | 0.3 | 0 | 0.05 | 0.3 |

- Note: enemies can only drop a maximum of 2 cards at a time

### Enemy Xp and Gold Drops
- Slugs can drop anywhere between 0 and 2 Gold, and give 5 XP when killed
- Vampire can drop anywhere between 0 and 8 Gold, and give 20 XP when killed
- Zombies can drop anywhere between 0 and 4 Gold, and give 10 XP when killed

### Enemy stats
- Enemies move in an anti-clockwise direction
- Speed is given relative to the character’s speed

### Allied soldiers
- The character can have a maximum of 2 allied soldiers
- When the character has more 1 or more Allied Soldiers, then a soldier will appear behind the character in the path and follow 1 square behind them.

### Rare items
- The Tree Stump reduces normal enemy damage to 1/3, and Boss damage to 1/4
- Anduril does 20 base damage, and 60 damage to bosses
- The value of Doggie Coin will only be available outside of the shop, so the Player has no idea what price they are selling for.
- The Value of Doggie Coin prior to Elan Muske appearing can be anywhere between 0 and 10000 Gold, after Elan Muske appears it can be anywhere between 0 and 10 Gold.
- The Reverse Path Potion will reverse the direction the character is moving in on the path when used.

### Inventory
- When clicking a non-consumable item in the inventory a popup appears displaying the Name, Description and sell price of that item along with a picture of it.
- Consumable items such as Health Potions and Reverse Path Potions can be used by clicking on them in the character's inventory.

| Type | Health | Damage | Battle radius | Support radius | Speed |
|------|--------|--------|---------------|----------------|-------|
| Slug | 4 | 1 | 1 | 2 | 1 |
| Zombie | 10 | 2,  For each attack on an allied soldier there is a 10% chance of a critical bite | 2 | 2 | 0.5 |
| Vampire | 15 | Standard: 3, Critical: 6, for each attack there is a 20% chance of a critical attack occurring | 2 | 3 | 1 |
| Doggie | 60 | 5 | 1 | 2 | 1 |
| ElanMuske | 120 | 10 | 1 | 2 | 1| 

### Attack item stats
| Type | Damage | Cost (gold) |
|------|--------|-------------|
| Sword | 10 | 10 |
| Stake | Normal: 6, Vampires: 20 | 15 |
| Staff | Normal: 3, Trance lasts 5 attack turns | 20 |
| Anduril | Normal: 20, Bosses: 60 | Cannot be bought, but can be sold for 50 Gold |


### Defence item stats
| Type | Defence | Cost (gold) |
|------|---------|-------------|
| Armour | Enemy attacks are halved | 20 |
| Shield | Vampire critical attack chance decreases by 60% | 10 |
| Helmet | Enemy attacks are reduced by 2, Damage to enemies is reduced by 2 | 15 |
| Tree Stump | Normal enemy damage is cut to 1/3, Bosses damage 1/4 | Cannot be bought, but can be sold for 50 Gold |

### Misc item stats
| Type | Effect | Cost (gold) |
|------|---------|-------------|
| Health Potion | Character's Health is restored | 30 |
| TheOneRing | Character respawns with full Health upon being killed one time | Cannot be bought, but can be sold for 50 Gold |
| DoggieCoin | Fluctuates in price | Cannot be bought, but can be sold for a random amount of Gold |


