acceuil - publication de tout le monde
SELECT ID_PUB, e.ID_USR,`LOGIN`, `ETAT` , DATEPUB ,`NOM_EVENT` as titre, concat (DESCRIPTION,'\n - ',`DATEDEBUT`, ' --> ', `DATEFIN` ,' à ',`LIEU`) as DESCRIPTION, `AFFICHE`,'evenement' FROM `evenement` e, user u
WHERE e.ID_USR = u.ID_USR
UNION
SELECT ID_PUB, e.ID_USR,`LOGIN`, `ETAT` , DATEPUB , concat(LIEUDEPART,' - ',LIEUARRIVE) as titre,  DESCRIPTION , '-', 'covoiturage' FROM `covoiturage` e, user u
WHERE e.ID_USR = u.ID_USR
UNION
SELECT ID_PUB, e.ID_USR, `LOGIN`,`ETAT` , DATEPUB ,concat(TYPELOGEMENT,' - ',TYPECHAMBRE,' - ',IMGCOUVERTURE,' - ',NBCHAMBRE) as titre, DESCRIPTION , '-','colocation' FROM `colocation` e, user u
WHERE e.ID_USR = u.ID_USR
UNION
SELECT ID_PUB, e.ID_USR, `LOGIN`,ETAT,DATEPUB,'Demande d aide' as titre , concat( DESCRIPTION,' - ',section, ' - ',matiere),DOCUMENT,'aide' FROM `aide` e, user u
WHERE e.ID_USR = u.ID_USR
GROUP BY DATEPUB desc

user profile
SELECT ID_PUB, e.ID_USR,`LOGIN`, `ETAT` , DATEPUB ,`NOM_EVENT` as titre, concat (DESCRIPTION,'\n - ',`DATEDEBUT`, ' --> ', `DATEFIN` ,' à ',`LIEU`) as DESCRIPTION, `AFFICHE`,'evenement' FROM `evenement` e, user u
WHERE e.ID_USR = u.ID_USR AND e.ID_USR = 57
UNION
SELECT ID_PUB, e.ID_USR,`LOGIN`, `ETAT` , DATEPUB , concat(LIEUDEPART,' - ',LIEUARRIVE) as titre,  DESCRIPTION , '-', 'covoiturage' FROM `covoiturage` e, user u
WHERE e.ID_USR = u.ID_USR AND e.ID_USR = 57
UNION
SELECT ID_PUB, e.ID_USR, `LOGIN`,`ETAT` , DATEPUB ,concat(TYPELOGEMENT,' - ',TYPECHAMBRE,' - ',IMGCOUVERTURE,' - ',NBCHAMBRE) as titre, DESCRIPTION , '-','colocation' FROM `colocation` e, user u
WHERE e.ID_USR = u.ID_USR AND e.ID_USR = 57
UNION
SELECT ID_PUB, e.ID_USR, `LOGIN`,ETAT,DATEPUB,'Demande d aide' as titre , concat( DESCRIPTION,' - ',section, ' - ',matiere),DOCUMENT,'aide' FROM `aide` e, user u
WHERE e.ID_USR = u.ID_USR AND e.ID_USR = 57
GROUP BY DATEPUB desc

admin stat
SELECT Sum( a.count ) FROM(
	SELECT Count( * ) AS count FROM covoiturage   UNION ALL
	SELECT Count( * ) AS count FROM evenement
	UNION ALL SELECT Count( * ) AS count FROM colocation
	UNION ALL SELECT Count( * ) AS count FROM user)  a

user stat // return 4 ligne dans l ordre

SELECT Count( * ) AS count FROM covoiturage  WHERE ID_USR = 45 
UNION ALL
SELECT Count( * ) AS count FROM evenement  WHERE ID_USR = 45 
UNION ALL 
SELECT Count( * ) AS count FROM colocation  WHERE ID_USR = 45 
UNION ALL 
SELECT Count( * ) AS count FROM aide  WHERE ID_USR = 45 

