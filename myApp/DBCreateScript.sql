-- Database: data

DROP DATABASE data;

CREATE DATABASE data
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\c data



-- Table: public.security

--DROP TABLE public.security CASCADE;

CREATE TABLE public.security
(
    secid character varying(12) COLLATE pg_catalog."default" NOT NULL,
    emitent_title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    id integer NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    regnumber character varying(63) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT security_pkey PRIMARY KEY (secid),
    CONSTRAINT uk_security UNIQUE (id)
)

TABLESPACE pg_default;
ALTER TABLE public.security
OWNER to postgres;



--Table: public.history

--DROP TABLE public.history;

CREATE TABLE public.history
(
    id character varying(22) COLLATE pg_catalog."default" NOT NULL,
    close double precision NOT NULL,
    numtrades integer NOT NULL,
    open double precision NOT NULL,
    secid character varying(12) COLLATE pg_catalog."default",
    tradedate character varying(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT history_pkey PRIMARY KEY (id),
    CONSTRAINT fk_security FOREIGN KEY (secid)
        REFERENCES public.security (secid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
ALTER TABLE public.history
OWNER to postgres;