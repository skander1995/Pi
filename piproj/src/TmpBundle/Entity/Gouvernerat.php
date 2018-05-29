<?php

namespace TmpBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Gouvernerat
 *
 * @ORM\Table(name="gouvernerat")
 * @ORM\Entity
 */
class Gouvernerat
{
    /**
     * @var integer
     *
     * @ORM\Column(name="ID_GOV", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idGov;

    /**
     * @var string
     *
     * @ORM\Column(name="LIB_GOV", type="string", length=100, nullable=true)
     */
    private $libGov;


}

