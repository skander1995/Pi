<?php

namespace EvenementBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use AdminUserBundle\Entity\User;

/**
 * Evenement
 *
 * @ORM\Table(name="evenements")
 * @ORM\Entity
 */
class Evenement
{
    /**
     * @var integer
     *
     * @ORM\Column(name="ID_PUB", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var User
     *
     * @ORM\ManyToOne(targetEntity="AdminUserBundle\Entity\User")
     * @ORM\JoinColumn(name="idUsr",referencedColumnName="id")
     */
    private $idUsr;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="DATEPUB", type="date", nullable=true)
     */
    private $datepub;

    /**
     * @var string
     *
     * @ORM\Column(name="DESCRIPTION", type="text", length=65535, nullable=true)
     */
    private $description;

    /**
     * @var string
     *
     * @ORM\Column(name="ETAT", type="string", length=20, nullable=true)
     */
    private $etat;

    /**
     * @var string
     *
     * @ORM\Column(name="NOM_EVENT", type="string", length=50, nullable=true)
     */
    private $nomEvent;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="DATEDEBUT", type="date", nullable=true)
     */
    private $datedebut;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="DATEFIN", type="date", nullable=true)
     */
    private $datefin;

    /**
     * @var string
     *
     * @ORM\Column(name="LIEU", type="string", length=50, nullable=true)
     */
    private $lieu;

    /**
     * @var string
     *
     * @ORM\Column(name="AFFICHE", type="text", length=65535, nullable=true)
     */
    private $affiche;

    /**
     * @var integer
     *
     * @ORM\Column(name="place_dispo", type="integer", nullable=false)
     */
    private $placeDispo;



    /**
     * Get idPub
     *
     * @return integer
     */
    public function getId()
    {
        return $this->id;
    }


    /**
     * Set datepub
     *
     * @param \DateTime $datepub
     *
     * @return Evenement
     */
    public function setDatepub($datepub)
    {
        $this->datepub = $datepub;

        return $this;
    }

    /**
     * Get datepub
     *
     * @return \DateTime
     */
    public function getDatepub()
    {
        return $this->datepub;
    }

    /**
     * Set description
     *
     * @param string $description
     *
     * @return Evenement
     */
    public function setDescription($description)
    {
        $this->description = $description;

        return $this;
    }

    /**
     * Get description
     *
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * Set etat
     *
     * @param string $etat
     *
     * @return Evenement
     */
    public function setEtat($etat)
    {
        $this->etat = $etat;

        return $this;
    }

    /**
     * Get etat
     *
     * @return string
     */
    public function getEtat()
    {
        return $this->etat;
    }

    /**
     * Set nomEvent
     *
     * @param string $nomEvent
     *
     * @return Evenement
     */
    public function setNomEvent($nomEvent)
    {
        $this->nomEvent = $nomEvent;

        return $this;
    }

    /**
     * Get nomEvent
     *
     * @return string
     */
    public function getNomEvent()
    {
        return $this->nomEvent;
    }

    /**
     * Set datedebut
     *
     * @param \DateTime $datedebut
     *
     * @return Evenement
     */
    public function setDatedebut($datedebut)
    {
        $this->datedebut = $datedebut;

        return $this;
    }

    /**
     * Get datedebut
     *
     * @return \DateTime
     */
    public function getDatedebut()
    {
        return $this->datedebut;
    }

    /**
     * Set datefin
     *
     * @param \DateTime $datefin
     *
     * @return Evenement
     */
    public function setDatefin($datefin)
    {
        $this->datefin = $datefin;

        return $this;
    }

    /**
     * Get datefin
     *
     * @return \DateTime
     */
    public function getDatefin()
    {
        return $this->datefin;
    }

    /**
     * Set lieu
     *
     * @param string $lieu
     *
     * @return Evenement
     */
    public function setLieu($lieu)
    {
        $this->lieu = $lieu;

        return $this;
    }

    /**
     * Get lieu
     *
     * @return string
     */
    public function getLieu()
    {
        return $this->lieu;
    }

    /**
     * Set affiche
     *
     * @param string $affiche
     *
     * @return Evenement
     */
    public function setAffiche($affiche)
    {
        $this->affiche = $affiche;

        return $this;
    }

    /**
     * Get affiche
     *
     * @return string
     */
    public function getAffiche()
    {
        return $this->affiche;
    }

    /**
     * Set placeDispo
     *
     * @param integer $placeDispo
     *
     * @return Evenement
     */
    public function setPlaceDispo($placeDispo)
    {
        $this->placeDispo = $placeDispo;

        return $this;
    }

    /**
     * Get placeDispo
     *
     * @return integer
     */
    public function getPlaceDispo()
    {
        return $this->placeDispo;
    }

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
}
