CREATE TABLE `Highscores` (
    `ID` INT NOT NULL AUTO_INCREMENT,
    `Time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `Name` VARCHAR(10) NOT NULL,
    `Score` INT NOT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;
