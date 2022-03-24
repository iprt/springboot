use `seata-order`;

truncate `order`;

use `seata-points`;

update points
set `quantity` = 100
where points_id = 1;

use `seata-storage`;

update storage
set quantity =100
where storage_id = 1;