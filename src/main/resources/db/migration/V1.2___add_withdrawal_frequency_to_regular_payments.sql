ALTER TABLE payment
ADD COLUMN IF NOT EXISTS withdrawal_period BIGINT;