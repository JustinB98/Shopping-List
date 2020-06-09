# Shopping List

App to manage a list when you go shopping and saves all data in a sqlite database

# Features

* Ability to add an item using the Floating Action Button (located when you scroll all the way up)
* Remove an item by swiping to the right
* Remove all items by pressing (in the options menu)
* Sort items ascending and desending by the following fields:
  * Date Added
  * Item name
  * Price
  * Purchase status
    * Ascending means non purchased items are shown first, decending means purchased items are shown first
* Edit an item
* Toggle showing details on an item (Details meaning the description)
* Show details for all the items by pressing the eye icon in the options menu
* Hide all details by pressing the eye with the slash in the options menu

# Extras
* User can press anywhere on the card view to toggle if an item was purchsed (except of course if they press another button)
* User can select the following options, each will have their own icon:
  * Food
  * Electronic
  * Book
  * Other
* The sorting options will be saved (in Shared Preferences)
* When adding or editing an item, the item name and price is required but the description is not. This means if a description is not given, then there will not be a 'Show Details' toggle button
