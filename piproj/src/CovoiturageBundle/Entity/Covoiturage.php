<?php

namespace CovoiturageBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Covoiturage
 *
 * @ORM\Table(name="covoiturage", indexes={@ORM\Index(name="FK_AVOIR_ARRIVER", columns={"ID_VILLE_ARR"}), @ORM\Index(name="FK_AVOIR_DEPART", columns={"VIL_ID_VILLE"})})
 * @ORM\Entity
 */
class Covoiturage
{
        /**
     * @var integer
     *
     * @ORM\Column(name="ID_PUB", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var integer
     *
     * @ORM\Column(name="ID_USR", type="integer", nullable=true)
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
     * @ORM\Column(name="LIEUDEPART", type="string", length=50, nullable=true)
     */
    private $lieudepart;

    /**
     * @var string
     *
     * @ORM\Column(name="LIEUARRIVE", type="string", length=50, nullable=true)
     */
    private $lieuarrive;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="DATEDEPART", type="date", nullable=true)
     */
    private $datedepart;

    /**
     * @var float
     *
     * @ORM\Column(name="PRIX", type="float", precision=10, scale=0, nullable=true)
     */
    private $prix;

    /**
     * @var string
     *
     * @ORM\Column(name="CHECKPOINTS", type="text", length=65535, nullable=true)
     */
    private $checkpoints;

    /**
     * @var integer
     * @Assert\Range(max="5",maxMessage="nombre de place doit etre inferieur a 5")
     * @ORM\Column(name="NBPLACE", type="integer", nullable=true)
     */
    private $nbplace;

    /**
     * @var float
     *
     * @ORM\Column(name="rating", type="float", nullable=true)
     */
    private $rating;


    /**
     * @var \TmpBundle\Entity\Ville
     *
     * @ORM\ManyToOne(targetEntity="TmpBundle\Entity\Ville")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="ID_VILLE_ARR", referencedColumnName="ID_VILLE")
     * })
     */
    private $idVilleArr;

    /**
     * @var \TmpBundle\Entity\Ville
     *
     * @ORM\ManyToOne(targetEntity="TmpBundle\Entity\Ville")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="VIL_ID_VILLE", referencedColumnName="ID_VILLE")
     * })
     */
    private $vilVille;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="TmpBundle\Entity\Ville", inversedBy="idPub")
     * @ORM\JoinTable(name="passer",
     *   joinColumns={
     *     @ORM\JoinColumn(name="ID_PUB", referencedColumnName="ID_PUB")
     *   },
     *   inverseJoinColumns={
     *     @ORM\JoinColumn(name="ID_VILLE", referencedColumnName="ID_VILLE")
     *   }
     * )
     */
    private $idVille;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->idVille = new \Doctrine\Common\Collections\ArrayCollection();
    }


    /**
     * Get idPub
     *
     * @return integer
     */
    public function getid()
    {
        return $this->id;
    }

    /**
     * Set idUsr
     *
     * @param integer $idUsr
     *
     * @return Covoiturage
     */
    public function setIdUsr($idUsr)
    {
        $this->idUsr = $idUsr;

        return $this;
    }

    /**
     * Get idUsr
     *
     * @return integer
     */
    public function getIdUsr()
    {
        return $this->idUsr;
    }

    /**
     * Set datepub
     *
     * @param \DateTime $datepub
     *
     * @return Covoiturage
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
     * @return Covoiturage
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
     * @return Covoiturage
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
     * Set lieudepart
     *
     * @param string $lieudepart
     *
     * @return Covoiturage
     */
    public function setLieudepart($lieudepart)
    {
        $this->lieudepart = $lieudepart;

        return $this;
    }

    /**
     * Get lieudepart
     *
     * @return string
     */
    public function getLieudepart()
    {
        return $this->lieudepart;
    }

    /**
     * Set lieuarrive
     *
     * @param string $lieuarrive
     *
     * @return Covoiturage
     */
    public function setLieuarrive($lieuarrive)
    {
        $this->lieuarrive = $lieuarrive;

        return $this;
    }

    /**
     * Get lieuarrive
     *
     * @return string
     */
    public function getLieuarrive()
    {
        return $this->lieuarrive;
    }

    /**
     * Set datedepart
     *
     * @param \DateTime $datedepart
     *
     * @return Covoiturage
     */
    public function setDatedepart($datedepart)
    {
        $this->datedepart = $datedepart;

        return $this;
    }

    /**
     * Get datedepart
     *
     * @return \DateTime
     */
    public function getDatedepart()
    {
        return $this->datedepart;
    }

    /**
     * Set prix
     *
     * @param float $prix
     *
     * @return Covoiturage
     */
    public function setPrix($prix)
    {
        $this->prix = $prix;

        return $this;
    }

    /**
     * Get prix
     *
     * @return float
     */
    public function getPrix()
    {
        return $this->prix;
    }

    /**
     * Set checkpoints
     *
     * @param string $checkpoints
     *
     * @return Covoiturage
     */
    public function setCheckpoints($checkpoints)
    {
        $this->checkpoints = $checkpoints;

        return $this;
    }

    /**
     * Get checkpoints
     *
     * @return string
     */
    public function getCheckpoints()
    {
        return $this->checkpoints;
    }

    /**
     * Set nbplace
     *
     * @param integer $nbplace
     *
     * @return Covoiturage
     */
    public function setNbplace($nbplace)
    {
        $this->nbplace = $nbplace;

        return $this;
    }

    /**
     * Get nbplace
     *
     * @return integer
     */
    public function getNbplace()
    {
        return $this->nbplace;
    }

    /**
     * Set idVilleArr
     *
     * @param \TmpBundle\Entity\Ville $idVilleArr
     *
     * @return Covoiturage
     */
    public function setIdVilleArr(\TmpBundle\Entity\Ville $idVilleArr = null)
    {
        $this->idVilleArr = $idVilleArr;

        return $this;
    }

    /**
     * Get idVilleArr
     *
     * @return \TmpBundle\Entity\Ville
     */
    public function getIdVilleArr()
    {
        return $this->idVilleArr;
    }

    /**
     * Set vilVille
     *
     * @param \TmpBundle\Entity\Ville $vilVille
     *
     * @return Covoiturage
     */
    public function setVilVille(\TmpBundle\Entity\Ville $vilVille = null)
    {
        $this->vilVille = $vilVille;

        return $this;
    }

    /**
     * Get vilVille
     *
     * @return \TmpBundle\Entity\Ville
     */
    public function getVilVille()
    {
        return $this->vilVille;
    }

    /**
     * Add idVille
     *
     * @param \TmpBundle\Entity\Ville $idVille
     *
     * @return Covoiturage
     */
    public function addIdVille(\TmpBundle\Entity\Ville $idVille)
    {
        $this->idVille[] = $idVille;

        return $this;
    }

    /**
     * Remove idVille
     *
     * @param \TmpBundle\Entity\Ville $idVille
     */
    public function removeIdVille(\TmpBundle\Entity\Ville $idVille)
    {
        $this->idVille->removeElement($idVille);
    }

    /**
     * Get idVille
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getIdVille()
    {
        return $this->idVille;
    }

    /**
     * @return float
     */
    public function getRating()
    {
        return $this->rating;
    }

    /**
     * @param float $rating
     */
    public function setRating($rating)
    {
        $this->rating = $rating;
    }

}
