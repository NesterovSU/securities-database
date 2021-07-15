-- Database: data

DROP DATABASE data;

CREATE DATABASE data;

\c data

-- Table: public.security

CREATE TABLE public.security
(
    secid character varying(12) NOT NULL,
    emitent_title character varying(255) NOT NULL,
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    regnumber character varying(63) NOT NULL,
    CONSTRAINT security_pkey PRIMARY KEY (secid)
);


--Table: public.history

CREATE TABLE public.history
(
    id character varying(22) NOT NULL,
    close double precision NOT NULL,
    numtrades integer NOT NULL,
    open double precision NOT NULL,
    secid character varying(12),
    tradedate character varying(10) NOT NULL,
    CONSTRAINT history_pkey PRIMARY KEY (id),
    CONSTRAINT fk_security FOREIGN KEY (secid)
        REFERENCES public.security (secid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
