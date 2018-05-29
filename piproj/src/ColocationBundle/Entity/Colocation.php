<?php

namespace ColocationBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Colocation
 *
 * @ORM\Table(name="colocation", indexes={@ORM\Index(name="FK_SITUER_COLOC", columns={"ID_VILLE"})})
 * @ORM\Entity
 */
class Colocation
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
     * @ORM\Column(name="LIEU", type="string", length=50, nullable=true)
     */
    private $lieu;

    /**
     * @var float
     *
     * @ORM\Column(name="LOYERMENSUEL", type="float", precision=10, scale=0, nullable=true)
     */
    private $loyermensuel;

    /**
     * @var string
     *
     * @ORM\Column(name="TYPELOGEMENT", type="string", length=50, nullable=true)
     */
    private $typelogement;

    /**
     * @var string
     *
     * @ORM\Column(name="TYPECHAMBRE", type="string", length=50, nullable=true)
     */
    private $typechambre;

    /**
     * @var string
     *
     * @ORM\Column(name="IMGCOUVERTURE", type="text", length=65535, nullable=true)
     */
    private $imgcouverture;

    /**
     * @var integer
     *
     * @ORM\Column(name="NBCHAMBRE", type="integer", nullable=true)
     */
    private $nbchambre;

    /**
     * @var string
     *
     * @ORM\Column(name="COMMODITE", type="string", length=250, nullable=true)
     */
    private $commodite;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="DATEDISPONIBILITE", type="date", nullable=true)
     */
    private $datedisponibilite;

    /**
     * @var \TmpBundle\Entity\Ville
     *
     * @ORM\ManyToOne(targetEntity="TmpBundle\Entity\Ville")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="ID_VILLE", referencedColumnName="ID_VILLE")
     * })
     */
    private $idVille;


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
     * Set idUsr
     *
     * @param integer $idUsr
     *
     * @return Colocation
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
     * @return Colocation
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
     * @return Colocation
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
     * @return Colocation
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
     * Set lieu
     *
     * @param string $lieu
     *
     * @return Colocation
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
     * Set loyermensuel
     *
     * @param float $loyermensuel
     *
     * @return Colocation
     */
    public function setLoyermensuel($loyermensuel)
    {
        $this->loyermensuel = $loyermensuel;

        return $this;
    }

    /**
     * Get loyermensuel
     *
     * @return float
     */
    public function getLoyermensuel()
    {
        return $this->loyermensuel;
    }

    /**
     * Set typelogement
     *
     * @param string $typelogement
     *
     * @return Colocation
     */
    public function setTypelogement($typelogement)
    {
        $this->typelogement = $typelogement;

        return $this;
    }

    /**
     * Get typelogement
     *
     * @return string
     */
    public function getTypelogement()
    {
        return $this->typelogement;
    }

    /**
     * Set typechambre
     *
     * @param string $typechambre
     *
     * @return Colocation
     */
    public function setTypechambre($typechambre)
    {
        $this->typechambre = $typechambre;

        return $this;
    }

    /**
     * Get typechambre
     *
     * @return string
     */
    public function getTypechambre()
    {
        return $this->typechambre;
    }

    /**
     * Set imgcouverture
     *
     * @param string $imgcouverture
     *
     * @return Colocation
     */
    public function setImgcouverture($imgcouverture)
    {
        $this->imgcouverture = $imgcouverture;

        return $this;
    }

    /**
     * Get imgcouverture
     *
     * @return string
     */
    public function getImgcouverture()
    {
        return $this->imgcouverture;
    }

    /**
     * Set nbchambre
     *
     * @param integer $nbchambre
     *
     * @return Colocation
     */
    public function setNbchambre($nbchambre)
    {
        $this->nbchambre = $nbchambre;

        return $this;
    }

    /**
     * Get nbchambre
     *
     * @return integer
     */
    public function getNbchambre()
    {
        return $this->nbchambre;
    }

    /**
     * Set commodite
     *
     * @param string $commodite
     *
     * @return Colocation
     */
    public function setCommodite($commodite)
    {
        $this->commodite = $commodite;

        return $this;
    }

    /**
     * Get commodite
     *
     * @return string
     */
    public function getCommodite()
    {
        return $this->commodite;
    }

    /**
     * Set datedisponibilite
     *
     * @param \DateTime $datedisponibilite
     *
     * @return Colocation
     */
    public function setDatedisponibilite($datedisponibilite)
    {
        $this->datedisponibilite = $datedisponibilite;

        return $this;
    }

    /**
     * Get datedisponibilite
     *
     * @return \DateTime
     */
    public function getDatedisponibilite()
    {
        return $this->datedisponibilite;
    }

    /**
     * Set idVille
     *
     * @param \TmpBundle\Entity\Ville $idVille
     *
     * @return Colocation
     */
    public function setIdVille(\TmpBundle\Entity\Ville $idVille = null)
    {
        $this->idVille = $idVille;

        return $this;
    }

    /**
     * Get idVille
     *
     * @return \TmpBundle\Entity\Ville
     */
    public function getIdVille()
    {
        return $this->idVille;
    }
}
