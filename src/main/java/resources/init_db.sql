CREATE TABLE `internet_shop`.`users` (
  `user_id` BIGINT(11) BIGINT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(256) NOT NULL,
  `username` VARCHAR(256) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`products` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `productName` VARCHAR(256) NOT NULL,
  `productPrice` VARCHAR(256) NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`product_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`orders` (
  `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`order_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`orders_products` (
  `product_id` BIGINT(11) NOT NULL,
  `order_id` BIGINT(11) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`roles` (
  `role_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`users_roles` (
  `user_id` BIGINT(11) NOT NULL,
  `role_id` BIGINT(11) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`shopping_carts` (
  `shopping_cart_id` BIGINT(11) NOT NULL,
  `user_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`shopping_cart_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`shopping_carts_products` (
  `shopping_cart_id` BIGINT(11) NOT NULL,
  `product_id` BIGINT(11) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

------ ADD FOREIGN KEYS --------
ALTER TABLE `internet_shop`.`users_roles`
ADD INDEX `users_roles_to_roles` (`role_id` ASC) VISIBLE;
;
ALTER TABLE `internet_shop`.`users_roles`
ADD CONSTRAINT `users_roles_fk`
  FOREIGN KEY (`role_id`)
  REFERENCES `internet_shop`.`roles` (`role_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `internet_shop`.`users_roles`
ADD INDEX `users_roles_to_users_idx` (`user_id` ASC) VISIBLE;
;
ALTER TABLE `internet_shop`.`users_roles`
ADD CONSTRAINT `users_roles_to_users`
  FOREIGN KEY (`user_id`)
  REFERENCES `internet_shop`.`users` (`user_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `internet_shop`.`orders`
ADD CONSTRAINT `orders_to_users`
  FOREIGN KEY (`user_id`)
  REFERENCES `internet_shop`.`users` (`user_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `internet_shop`.`shopping_carts`
ADD INDEX `shopping_carts_to_users_idx` (`user_id` ASC) VISIBLE;
;
ALTER TABLE `internet_shop`.`shopping_carts`
ADD CONSTRAINT `shopping_carts_to_users`
  FOREIGN KEY (`user_id`)
  REFERENCES `internet_shop`.`users` (`user_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `internet_shop`.`shopping_carts_products`
ADD INDEX `shopping_cart_proucts_to_shopping_carts_idx` (`shopping_cart_id` ASC) VISIBLE;
;
ALTER TABLE `internet_shop`.`shopping_carts_products`
ADD CONSTRAINT `shopping_cart_proucts_to_shopping_carts`
  FOREIGN KEY (`shopping_cart_id`)
  REFERENCES `internet_shop`.`shopping_carts` (`shopping_cart_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `internet_shop`.`shopping_carts_products`
ADD INDEX `shopping_cart_products_to_products_idx` (`product_id` ASC) VISIBLE;
;
ALTER TABLE `internet_shop`.`shopping_carts_products`
ADD CONSTRAINT `shopping_cart_products_to_products`
  FOREIGN KEY (`product_id`)
  REFERENCES `internet_shop`.`products` (`product_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `internet_shop`.`orders_products`
ADD INDEX `orders_products_to_products_idx` (`product_id` ASC) VISIBLE;
;
ALTER TABLE `internet_shop`.`orders_products`
ADD CONSTRAINT `orders_products_to_products`
  FOREIGN KEY (`product_id`)
  REFERENCES `internet_shop`.`products` (`product_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `internet_shop`.`orders_products`
ADD INDEX `orders_produts_to_orders_idx` (`order_id` ASC) VISIBLE;
;
ALTER TABLE `internet_shop`.`orders_products`
ADD CONSTRAINT `orders_produts_to_orders`
  FOREIGN KEY (`order_id`)
  REFERENCES `internet_shop`.`orders` (`order_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

INSERT INTO `internet_shop`.`roles` (`role_id`, `role_name`) VALUES ('1', 'ADMIN');
INSERT INTO `internet_shop`.`roles` (`role_id`, `role_name`) VALUES ('2', 'USER');

ALTER TABLE `internet_shop`.`orders`
ADD COLUMN `deleted` TINYINT NULL DEFAULT 0 AFTER `user_id`;
