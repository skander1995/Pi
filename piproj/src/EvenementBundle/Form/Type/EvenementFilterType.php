<?php

namespace EvenementBundle\Form\Type;

use Doctrine\ORM\Mapping\Entity;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\DateFilterType;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\EntityFilterType;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\NumberFilterType;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\TextFilterType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class EvenementFilterType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
        
            ->add('idUsr', NumberFilterType::class)
            ->add('datepub', DateFilterType::class)
            ->add('description', TextFilterType::class)
            ->add('etat', TextFilterType::class)
            ->add('nomEvent', TextFilterType::class)
            ->add('datedebut', DateFilterType::class)
            ->add('datefin', DateFilterType::class)
            ->add('lieu', TextFilterType::class)
            ->add('affiche', TextFilterType::class)
            ->add('placeDispo', NumberFilterType::class)
            ->add('idVilleS', EntityFilterType::class, array('class' => 'TmpBundle\Entity\Ville'))
        ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class'        => 'EvenementBundle\Entity\Evenement',
            'csrf_protection'   => false,
            'validation_groups' => array('filter'),
            'method'            => 'GET',
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getName()
    {
        return 'evenement_filter';
    }
}
