<?php

namespace FaqBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Question
 *
 * @ORM\Table(name="question")
 * @ORM\Entity(repositoryClass="FaqBundle\Repository\QuestionRepository")
 */

class Question
{
    /**
     * @var integer
     *
     * @ORM\Column(name="ID_PUB", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idPub;

    /**
     * @var integer
     *@ORM\ManyToOne(targetEntity="AdminUserBundle\Entity\User")
     *@ORM\JoinColumn(name="idUsr",referencedColumnName="id")
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
     * @ORM\Column(name="SUJET", type="string", length=100, nullable=true)
     */
    private $sujet;

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
     * Get idPub
     *
     * @return integer
     */
    public function getIdPub()
    {
        return $this->idPub;
    }

    /**
     * Set idUsr
     *
     * @param integer $idUsr
     *
     * @return Question
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
     * @return Question
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
     * @return Question
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
     * @return Question
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
     * Set sujet
     *
     * @param string $sujet
     *
     * @return Question
     */
    public function setSujet($sujet)
    {
        $this->sujet = $sujet;

        return $this;
    }

    /**
     * Get sujet
     *
     * @return string
     */
    public function getSujet()
    {
        return $this->sujet;
    }
}
