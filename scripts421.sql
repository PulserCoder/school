ALTER TABLE student ADD CONSTRAINT age_constraint CHECK ( age > 16 );

ALTER TABLE student ADD CONSTRAINT unique_name UNIQUE (name),
                    ALTER COLUMN name SET NOT NULL ;

ALTER TABLE faculty ADD CONSTRAINT unique_pairs UNIQUE (name, color);

ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;



