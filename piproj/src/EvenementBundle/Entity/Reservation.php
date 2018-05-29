<?php

namespace EvenementBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use AdminUserBundle\Entity\User;

/**
 * Reservation
 *
 * @ORM\Table(name="reservation")
 * @ORM\Entity
 */
class Reservation
{
    /**
     * @var User
     * @ORM\ManyToOne(targetEntity="AdminUserBundle\Entity\User")
     * @ORM\JoinColumn(name="ID_USR",referencedColumnName="id")
     */
    private $idUsr;

    /**
     * @var Evenement
     * @ORM\ManyToOne(targetEntity="EvenementBundle\Entity\Evenement")
     * @ORM\JoinColumn(name="ID_PUB",referencedColumnName="ID_PUB")
     */
    private $idPub;

    /**
     * @ORM\Column(name="code", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $code = '0';

    /**
     * @return User
     */
    public function getIdUsr()
    {
        return $this->idUsr;
    }

    /**
     * @param User $idUsr
     */
    public function setIdUsr($idUsr)
    {
        $this->idUsr = $idUsr;
    }

    /**
     * @return Evenement
     */
    public function getIdPub()
    {
        return $this->idPub;
    }

    /**
     * @param Evenement $idPub
     */
    public function setIdPub($idPub)
    {
        $this->idPub = $idPub;
    }

    /**
     * @return mixed
     */
    public function getCode()
    {
        return $this->code;
    }

    /**
     * @param mixed $code
     */
    public function setCode($code)
    {
        $this->code = $code;
    }


}