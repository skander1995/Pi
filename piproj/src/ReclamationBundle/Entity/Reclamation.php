<?php

namespace ReclamationBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Reclamation
 *
 * @ORM\Table(name="reclamation", indexes={@ORM\Index(name="FK_CONCERNE", columns={"ID_USR"})})
 * @ORM\Entity
 */
class Reclamation
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
     * @var \AdminUserBundle\Entity\User
     *
     * @ORM\ManyToOne(targetEntity="AdminUserBundle\Entity\User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="USE_ID_USR", referencedColumnName="id")
     * })
     */
    private $useIdUsr;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="DATEPUB", type="date", nullable=true)
     */
    private $datepub;

    /**
     * @var string
     *
     * @ORM\Column(name="SUJET_REC", type="string", length=100, nullable=true)
     */
    private $sujetRec;

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
     * @ORM\Column(name="TYPE_REC", type="string", length=50, nullable=true)
     */
    private $typeRec;

    /**
     * @var \AdminUserBundle\Entity\User
     *
     * @ORM\ManyToOne(targetEntity="AdminUserBundle\Entity\User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="ID_USR", referencedColumnName="id")
     * })
     */
    private $idUsr;


    /**
     * Get id
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
     * @return Reclamation
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
     * Set sujetRec
     *
     * @param string $sujetRec
     *
     * @return Reclamation
     */
    public function setSujetRec($sujetRec)
    {
        $this->sujetRec = $sujetRec;

        return $this;
    }

    /**
     * Get sujetRec
     *
     * @return string
     */
    public function getSujetRec()
    {
        return $this->sujetRec;
    }

    /**
     * Set description
     *
     * @param string $description
     *
     * @return Reclamation
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
     * @return Reclamation
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
     * Set typeRec
     *
     * @param string $typeRec
     *
     * @return Reclamation
     */
    public function setTypeRec($typeRec)
    {
        $this->typeRec = $typeRec;

        return $this;
    }

    /**
     * Get typeRec
     *
     * @return string
     */
    public function getTypeRec()
    {
        return $this->typeRec;
    }

    /**
     * Set idUsr
     *
     * @param \AdminUserBundle\Entity\User $idUsr
     *
     * @return Reclamation
     */
    public function setIdUsr(\AdminUserBundle\Entity\User $idUsr = null)
    {
        $this->idUsr = $idUsr;

        return $this;
    }


    /**
     * Get idUsr
     *
     * @return \AdminUserBundle\Entity\User
     */
    public function getIdUsr()
    {
        return $this->idUsr;
    }

    /**
     * Set useIdUsr
     *
     * @param \AdminUserBundle\Entity\User $useIdUsr
     *
     * @return Reclamation
     */
    public function setUseIdUsr(\AdminUserBundle\Entity\User $useIdUsr = null)
    {
        $this->useIdUsr = $useIdUsr;

        return $this;
    }

    /**
     * Get useIdUsr
     *
     * @return \AdminUserBundle\Entity\User
     */
    public function getUseIdUsr()
    {
        return $this->useIdUsr;
    }
}
