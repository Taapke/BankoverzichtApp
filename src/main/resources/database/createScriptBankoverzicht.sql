CREATE SCHEMA Bankoverzicht;

CREATE TABLE Bankoverzicht.Transactie
(
  volgnummer int(8),
  boekingsdatum datetime,
  opdrachtgeversrekening varchar(18),
  tegenrekeningnummer varchar(34),
  saldo_voor_mutatie decimal(11,2),
  transactie_bedrag decimal(11,2),
  interne_transactiecode int(4),
  omschrijving varchar(140),
--   valutasoort_rekening varchar(3),
--   betalingskenmerk varchar(16),
--   journaaldatum datetime,
--   valutadatum datetime,
--   afschriftnummer int(3),
  CONSTRAINT transactie_pkey PRIMARY KEY (volgnummer)
);

CREATE TABLE Bankoverzicht.Transactiecode
(
  interne_code int(4),
  globale_code varchar(3),
  omschrijving varchar(34),
  CONSTRAINT transactiecode_pkey PRIMARY KEY (interne_code)
);

CREATE TABLE Bankoverzicht.Tegenrekening
(
  rekeningnummer varchar(34),
  rekeningnaam varchar(70),
  CONSTRAINT tegenrekening_pkey PRIMARY KEY (rekeningnummer)
);

CREATE USER 'user'@'localhost' IDENTIFIED BY 'pw';
GRANT ALL PRIVILEGES ON Bankoverzicht. * TO 'user'@'localhost';

-- INSERT INTO Tegenrekening (rekeningnummer, rekeningnaam) VALUES ('AB00987654321', 'B. Spindel');

