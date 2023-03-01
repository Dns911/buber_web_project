#insert into users () values (id,  'Den', 4524);
# insert into users () values (id,  'Ali', 44222524);
# insert into users () values (id,  'Миша', 74227824);
#  SELECT rate_driver FROM orders WHERE drivers_shifts_id = ANY (select drivers_shifts.id from drivers_shifts where driver_id = '7') ;
SELECT rate_client FROM orders WHERE drivers_shifts_id = ANY (select drivers_shifts.id from drivers_shifts where driver_id = '7')
