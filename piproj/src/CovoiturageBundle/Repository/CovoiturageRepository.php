<?php
/**
 * Created by PhpStorm.
 * User: cobwi
 * Date: 22/03/2018
 * Time: 15:52
 */

namespace CovoiturageBundle\Repository;
use Doctrine\ORM\EntityRepository;
class CovoiturageRepository extends \Doctrine\ORM\EntityRepository
{
    public function rechercheAnnonce($datedepart,$lieudepart,$lieuarrive)
    {
        $datedepart = "%".$datedepart."%";
        $query=$this->getEntityManager()->createQuery(
            "SELECT c from CovoiturageBundle:Covoiturage c

                   WHERE c.datedepart LIKE :datedepart AND c.lieuarrive LIKE :lieuarrive AND c.lieudepart LIKE :lieudepart ")
            ->setParameters(array('datedepart'=>$datedepart,'lieuarrive'=>$lieuarrive,'lieudepart'=>$lieudepart));


        return $query->getResult();

    }
}