<?php

namespace EducBundle\Entity;

use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;

/**
 * Aide
 *
     * @ORM\Table(name="aide")
 * @ORM\Entity(repositoryClass="EducBundle\Repository\aideRepository")
 */
class Aide
{
    public function __construct()
{
}
    /**
     * @var integer
     *
     * @ORM\Column(name="idPub", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idpub;



    //* @ORM\ManyToOne(targetEntity="UserBundle\Entity\User")
    // @ORM\JoinColumn(name="idUsr",referencedColumnName="id")
    /**
     *@ORM\ManyToOne(targetEntity="AdminUserBundle\Entity\User")
     *@ORM\JoinColumn(name="idUsr",referencedColumnName="id")
     */
    private $idusr;

    /**
     *
     * @ORM\Column(name="datepub", type="date")
     */
    private $datepub ;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="string", length=255, nullable=true)

         *@Assert\NotBlank()
     */
    private $description;

    /**
     * @var string
     *@Assert\NotBlank()
     * @Assert\File()
     * @ORM\Column(name="document", type="string", length=255, nullable=true)
     */
        private $document;

    /**
     * @var string
     *@Assert\NotBlank()
     * @ORM\Column(name="section", type="string", length=255, nullable=true)
     */
    private $section;

    /**
     * @var string
     *@Assert\NotBlank()
     * @ORM\Column(name="matiere", type="string", length=255, nullable=true)
     */
    private $matiere;



    /**
     * Get idpub
     *
     * @return integer
     */
    public function getIdpub()
    {
        return $this->idpub;
    }

    
    /**
     * Set datepub
     *
     * @param \DateTime $datepub
     *
     * @return Aide
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
     * @return Aide
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
     * Set document
     *
     * @param string $document
     *
     * @return Aide
     */
    public function setDocument($document)
    {
        $this->document = $document;

        return $this;
    }

    /**
     * Get document
     *
     * @return string
     */
    public function getDocument()
    {
        return $this->document;
    }

    /**
     * Set section
     *
     * @param string $section
     *
     * @return Aide
     */
    public function setSection($section)
    {
        $this->section = $section;

        return $this;
    }

    /**
     * Get section
     *
     * @return string
     */
    public function getSection()
    {
        return $this->section;
    }

    /**
     * Set matiere
     *
     * @param string $matiere
     *
     * @return Aide
     */
    public function setMatiere($matiere)
    {
        $this->matiere = $matiere;

        return $this;
    }

    /**
     * Get matiere
     *
     * @return string
     */
    public function getMatiere()
    {
        return $this->matiere;
    }

    /**
     * @return mixed
     */
    public function getIdusr()
    {
        return $this->idusr;
    }

    /**
     * @param mixed $idusr
     */
    public function setIdusr($idusr)
    {
        $this->idusr = $idusr;
    }



}
