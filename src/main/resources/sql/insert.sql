INSERT INTO race (id, name) VALUES (1, 'Half-Elf'), (2, 'Human'), (3, 'Wood-Elf'), (4, 'Half-Orc');
INSERT INTO class (id, name) VALUES (1, 'Wizard'), (2, 'Fighter'), (3, 'Cleric'), (4, 'Rogue');

INSERT INTO avatar (id, race_id, charClass_id, name, level, hitPoints, personalityTrait, bond, ideals, flaws, strength, dexterity, constitution, intelligence, wisdom, charisma, armorClass, imagePath) VALUES (1, 1, 1, 'Kiir Bravan', 1, 8, 'I\'m finally smart enough to realize I\’ll never be good enough.',  "I'm finally courageous enough to fight for friends and family.", 'Power is cool but only if used to protect the innocent.', "No matter how hard you try, you’ll lose everything you love.", 10, 14, 14, 16, 12, 10, 12, "/img/avatar/kiir_bravan.png");
INSERT INTO avatar (id, race_id, charClass_id, name, level, hitPoints, personalityTrait, bond, ideals, flaws, strength, dexterity, constitution, intelligence, wisdom, charisma, armorClass, imagePath) VALUES (2, 2, 2, 'Meatface', 1, 12, 'I like the sound of snapping bones. It relaxes me.', "My greataxe is a family heirloom, and it’s by far my most precious possession.", 'I must find the Temple of the Golden Chicken. There, the curse of my family can be lifted.', 'I have anger issues.', 15, 8, 14, 10, 12, 13, 17, "/img/avatar/meatface.png");
INSERT INTO avatar (id, race_id, charClass_id, name, level, hitPoints, personalityTrait, bond, ideals, flaws, strength, dexterity, constitution, intelligence, wisdom, charisma, armorClass, imagePath) VALUES (3, 3, 3, 'Lyan Amaranthia', 1, 9, 'Once abandoned yourself, you would never abandon someone who needs your help.', 'All life is precious and should be preserved. (Especially horses.)', 'I believe in my heart that I am meant for great things.', 'I have blind faith in nigh-omnipotent father figures.', 14, 10, 13, 10, 16, 12, 18, "/img/avatar/lyan_amaranthia.png");
INSERT INTO avatar (id, race_id, charClass_id, name, level, hitPoints, personalityTrait, bond, ideals, flaws, strength, dexterity, constitution, intelligence, wisdom, charisma, armorClass, imagePath) VALUES (4, 1, 2, 'Ari Strongbow', 1, 12, "Don’t forgive. Don’t forget.", "You aren’t worth the cost of the arrows it would take to kill you.", "I will have vengeance for my brother’s murder and the betrayal that caused it.", "I'm kind of a drama bomb.", 14, 16, 14, 10, 12, 10, 14, "/img/avatar/ari_strongbow.png");
INSERT INTO avatar (id, race_id, charClass_id, name, level, hitPoints, personalityTrait, bond, ideals, flaws, strength, dexterity, constitution, intelligence, wisdom, charisma, armorClass, imagePath) VALUES (5, 4, 4, 'Keth Silverson', 1, 10, 'I have trouble saying no to pretty girls… and mad scientists.', "You know what? What’s mine is actually mine Give it back.", "What’s mine is yours, and what’s yours is mine.", 'I have problems trusting my allies.', 10, 15, 13, 13, 10, 14, 13, "/img/avatar/keth_silverson.png");

INSERT INTO language(id, name) VALUES (1, 'Goblin');

INSERT INTO monstertemplate (id, language_id, race, hitPointNrOfDice, hitPointNSidedDice, hitPointAddition, strength, dexterity, constitution, intelligence, wisdom, charisma, armorClass, imagePath, experience) VALUES (1, null, "Stirge", 1, 4, 0, 4, 16, 11, 2, 8, 6, 14, "/img/monster/stirge.jpg", 25);
INSERT INTO monstertemplate (id, language_id, race, hitPointNrOfDice, hitPointNSidedDice, hitPointAddition, strength, dexterity, constitution, intelligence, wisdom, charisma, armorClass, imagePath, experience) VALUES (2, 1, "Goblin", 2, 6, 0, 8, 14, 10, 10, 8, 8, 15, "/img/monster/goblin.jpg", 50);

INSERT INTO itemtype(id, name) VALUES (1, 'Weapon'), (2, 'Equipment'), (3, 'Consumable');

INSERT INTO item (id, itemType_id, name, atkBonus, dmgNrOfDice, dmgNSidedDice, dmgAddition) VALUES (1, 1, "Dagger", 4, 1, 4, 2), (2, 1, "Quarterstaff", 2, 1, 6, 0), (3, 1, "Mace", 4, 1, 6, 2), (4, 1, "Greataxe", 5, 1, 12, 3), (5, 1, "Javelin", 5, 1, 6, 3), (6, 1, "Longbow", 7, 1, 8, 3), (7, 1, "Longsword", 4, 1, 8, 2), (8, 1, "Scimitar", 4, 1, 6, 2);

INSERT INTO avatar_item (avatar_id, item_id) VALUES (1, 2), (2, 4), (2, 5), (3, 3), (4, 6), (4, 7), (5, 1), (5, 1);

INSERT INTO monster_item (monster_id, item_id) VALUES (2, 8);

INSERT INTO spell(id, level, name, atkBonus, dmgNrOfDice, dmgNSidedDice, dmgAddition, heal, targetSelf) VALUES (1, 0, 'Blood Drain', 5, 1, 4, 3, false, false), (2, 0, 'Second Wind', 0, 1, 10, 1, true, true), (3, 1, 'Healing Word', 0, 1, 4, 2, true, false), (4, 1, 'Magic Missile', 5, 1, 4, 1, false, false);

INSERT INTO monster_spell(monster_id, spell_id) VALUES(1, 1);
INSERT INTO avatar_spell(avatar_id, spell_id) VALUES(2, 2), (3, 3), (1, 4);

INSERT INTO avatar_language(avatar_id, language_id) VALUES(4, 1), (1, 1);