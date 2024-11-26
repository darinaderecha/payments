ALTER TABLE regular_payment
ADD COLUMN IF NOT EXISTS withdrawal_period BIGINT;