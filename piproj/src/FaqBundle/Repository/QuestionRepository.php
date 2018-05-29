<?php
/**
 * Created by PhpStorm.
 * User: cobwi
 * Date: 28/03/2018
 * Time: 10:19
 */


namespace FaqBundle\Repository;

use Doctrine\ORM\EntityRepository;

class QuestionRepository extends EntityRepository
{
    public function findQuestion($param)
    {
        $query = $this->getEntityManager()
            ->createQuery("
            select v from FaqBundle:Question v 
            WHERE  
            v.description like :param 
            OR 
            v.sujet like :param
            ");

        $query->setParameter("param", '%' . $param . '%');
        return $query->getResult();
    }
}