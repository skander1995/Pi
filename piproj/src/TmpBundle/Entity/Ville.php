<?php

namespace TmpBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Ville
 *
 * @ORM\Table(name="ville", indexes={@ORM\Index(name="FK_APPARTIENT", columns={"ID_GOV"})})
 * @ORM\Entity
 */
class Ville
{
    /**
     * @var integer
     *
     * @ORM\Column(name="ID_VILLE", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idVille;

    /**
     * @var string
     *
     * @ORM\Column(name="NOM_VILLE", type="string", length=250, nullable=true)
     */
    private $nomVille;

    /**
     * @var \TmpBundle\Entity\Gouvernerat
     *
     * @ORM\ManyToOne(targetEntity="TmpBundle\Entity\Gouvernerat")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="ID_GOV", referencedColumnName="ID_GOV")
     * })
     */
    private $idGov;

    /**
     * @var \Doctrine\Common\Collections\Collection
     *
     * @ORM\ManyToMany(targetEntity="CovoiturageBundle\Entity\Covoiturage", mappedBy="idVille")
     */
    private $idPub;


    /**
     * Constructor
     */
    public function __construct()
    {
        $this->idPub = new \Doctrine\Common\Collections\ArrayCollection();

    }


    /**
     * Get idVille
     *
     * @return integer
     */
    public function getIdVille()
    {
        return $this->idVille;
    }

    /**
     * Set nomVille
     *
     * @param string $nomVille
     *
     * @return Ville
     */
    public function setNomVille($nomVille)
    {
        $this->nomVille = $nomVille;

        return $this;
    }

    /**
     * Get nomVille
     *
     * @return string
     */
    public function getNomVille()
    {
        return $this->nomVille;
    }

    /**
     * Set idGov
     *
     * @param \TmpBundle\Entity\Gouvernerat $idGov
     *
     * @return Ville
     */
    public function setIdGov(\TmpBundle\Entity\Gouvernerat $idGov = null)
    {
        $this->idGov = $idGov;

        return $this;
    }

    /**
     * Get idGov
     *
     * @return \TmpBundle\Entity\Gouvernerat
     */
    public function getIdGov()
    {
        return $this->idGov;
    }

    /**
     * Add idPub
     *
     * @param \CovoiturageBundle\Entity\Covoiturage $idPub
     *
     * @return Ville
     */
    public function addIdPub(\CovoiturageBundle\Entity\Covoiturage $idPub)
    {
        $this->idPub[] = $idPub;

        return $this;
    }

    /**
     * Remove idPub
     *
     * @param \CovoiturageBundle\Entity\Covoiturage $idPub
     */
    public function removeIdPub(\CovoiturageBundle\Entity\Covoiturage $idPub)
    {
        $this->idPub->removeElement($idPub);
    }

    /**
     * Get idPub
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getIdPub()
    {
        return $this->idPub;
    }



    public function __toString()
    {
        // TODO: Implement __toString() method.
        return (string)$this->idVille;
    }

}

