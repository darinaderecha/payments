alter table regular_payment
add column if not exists withdrawal_period timestamp;