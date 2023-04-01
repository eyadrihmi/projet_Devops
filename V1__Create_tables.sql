CREATE TABLE operateur (
    id_operateur SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE detail_fournisseur (
    id_detail_fournisseur SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    date_debut_collaboration DATE NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    matricule VARCHAR(255) NOT NULL,
    fournisseur_id BIGINT REFERENCES fournisseur(id_fournisseur)
);

CREATE TABLE fournisseur (
    id_fournisseur SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    tel VARCHAR(255) NOT NULL,
    fax VARCHAR(255) NOT NULL,
    site_web VARCHAR(255) NOT NULL,
    secteur_activites_id BIGINT REFERENCES secteur_activite(id_secteur_activite),
    detail_fournisseur_id BIGINT REFERENCES detail_fournisseur(id_detail_fournisseur)
);

CREATE TABLE secteur_activite (
    id_secteur_activite SERIAL PRIMARY KEY,
    code_secteur_activite VARCHAR(255) NOT NULL,
    libelle_secteur_activite VARCHAR(255) NOT NULL
);

CREATE TABLE stock (
    id_stock SERIAL PRIMARY KEY,
    libelle_stock VARCHAR(255) NOT NULL,
    qte INTEGER NOT NULL,
    qte_min INTEGER NOT NULL
);

CREATE TABLE produit (
    id_produit SERIAL PRIMARY KEY,
    libelle_produit VARCHAR(255) NOT NULL,
    ref_produit VARCHAR(255) NOT NULL,
    prix_produit FLOAT NOT NULL,
    stock_id BIGINT REFERENCES stock(id_stock)
);

CREATE TABLE facture (
    id_facture SERIAL PRIMARY KEY,
    montant_remise FLOAT NOT NULL,
    montant_facture FLOAT NOT NULL,
    date_creation_facture DATE NOT NULL,
    date_derniere_modification_facture DATE NOT NULL,
    archivee BOOLEAN NOT NULL,
    fournisseur_id BIGINT REFERENCES fournisseur(id_fournisseur)
);

CREATE TABLE detail_facture (
    id_detail_facture SERIAL PRIMARY KEY,
    quantite_produit INTEGER NOT NULL,
    prix_unitaire_produit FLOAT NOT NULL,
    facture_id BIGINT REFERENCES facture(id_facture),
    produit_id BIGINT REFERENCES produit(id_produit)
);

CREATE TABLE reglement (
    id_reglement SERIAL PRIMARY KEY,
    date_reglement DATE NOT NULL,
    montant_reglement FLOAT NOT NULL,
    facture_id BIGINT REFERENCES facture(id_facture)
);
