<?php

namespace EducBundle\Entity;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;

/**
 * Commentaire
 *
 * @ORM\Table( name="commentaire", indexes={@ORM\Index(name="FK_CONTENIR", columns={"ID_PUB"})})
 * @ORM\Entity
 */
class Commentaire
{
    public function __construct()
    {
    }
    /**
     * @var integer
     *
     * @ORM\Column(name="ID_COM", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idCom;

    /**
     * @var integer
     *
     * @ORM\Column(name="ID_PUB", type="integer", nullable=false)
     */
    private $idPub;

    /**
     * @var string
     *@Assert\NotBlank()
     * @ORM\Column(name="CONTENU_COM", type="text", length=65535, nullable=true)
     */
    private $contenuCom;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="DATE_COM", type="datetime", nullable=true)
     */
    private $dateCom;



    /**
     *@ORM\ManyToOne(targetEntity="AdminUserBundle\Entity\User")
     *@ORM\JoinColumn(name="idUsr",referencedColumnName="id")
     */
    private $idUsr;

    /**
     * @var string
     *
     * @ORM\Column(name="username", type="string", length=255, nullable=true)
     */
    private $username;
    /**
     * @var string
     *
     * @ORM\Column(name="photoprofile", type="text", nullable=true)
     */
    private $photoprofile;


    /**
     * Get idCom
     *
     * @return integer
     */
    public function getIdCom()
    {
        return $this->idCom;
    }

    /**
     * Set idPub
     *
     * @param integer $idPub
     *
     * @return Commentaire
     */
    public function setIdPub($idPub)
    {
        $this->idPub = $idPub;

        return $this;
    }

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
     * Set contenuCom
     *
     * @param string $contenuCom
     *
     * @return Commentaire
     */
    public function setContenuCom($contenuCom)
    {
        $this->contenuCom = $contenuCom;

        return $this;
    }

    /**
     * Get contenuCom
     *
     * @return string
     */
    public function getContenuCom()
    {
        return $this->contenuCom;
    }

    /**
     * Set dateCom
     *
     * @param \DateTime $dateCom
     *
     * @return Commentaire
     */
    public function setDateCom($dateCom)
    {
        $this->dateCom = $dateCom;

        return $this;
    }

    /**
     * Get dateCom
     *
     * @return \DateTime
     */
    public function getDateCom()
    {
        return $this->dateCom;
    }

    /**
     * @return mixed
     */
    public function getIdUsr()
    {
        return $this->idUsr;
    }

    /**
     * @param mixed $idUsr
     */
    public function setIdUsr($idUsr)
    {
        $this->idUsr = $idUsr;
    }


    /**
     * Set username
     *
     * @param string $username
     *
     * @return Commentaire
     */
    public function setUsername($username)
    {
        $this->username = $username;

        return $this;
    }

    /**
     * Get username
     *
     * @return string
     */
    public function getUsername()
    {
        return $this->username;
    }

    /**
     * Set photoprofile
     *
     * @param string $photoprofile
     *
     * @return Commentaire
     */
    public function setPhotoprofile($photoprofile)
    {
        $this->photoprofile = $photoprofile;

        return $this;
    }

    /**
     * Get photoprofile
     *
     * @return string
     */
    public function getPhotoprofile()
    {
        return $this->photoprofile;
    }
}
