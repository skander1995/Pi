<?php

namespace ReclamationBundle\Form\Type;

use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\DateFilterType;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\EntityFilterType;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\TextFilterType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReclamationFilterType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
        
            ->add('datepub', DateFilterType::class)
            ->add('sujetRec', TextFilterType::class)
            ->add('description', TextFilterType::class)
            ->add('etat', TextFilterType::class)
            ->add('typeRec', TextFilterType::class)
            ->add('useIdUsr', EntityFilterType::class, array('class' => 'AdminUserBundle\Entity\User'))
            ->add('idUsr', EntityFilterType::class, array('class' => 'AdminUserBundle\Entity\User'))
        ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class'        => 'ReclamationBundle\Entity\Reclamation',
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
        return 'reclamation_filter';
    }
}
