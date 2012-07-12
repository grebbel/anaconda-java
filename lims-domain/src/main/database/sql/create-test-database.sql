DROP DATABASE IF EXISTS lims_dev;
CREATE DATABASE IF NOT EXISTS lims_tst DEFAULT CHARACTER SET = utf8;
GRANT ALL ON lims_tst.* TO 'lims_tst'@'localhost' IDENTIFIED BY 'lims_tst';
