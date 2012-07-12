DROP DATABASE IF EXISTS lims_dev ;
CREATE DATABASE IF NOT EXISTS lims_dev DEFAULT CHARACTER SET = utf8;
GRANT ALL ON lims_dev.* TO 'lims_dev'@'localhost' IDENTIFIED BY 'lims_dev';
