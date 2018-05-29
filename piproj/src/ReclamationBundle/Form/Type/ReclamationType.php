<?php

namespace ReclamationBundle\Form\Type;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReclamationType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
        
            ->add('datepub')
            ->add('sujetRec')
            ->add('description')
            ->add('etat')
            ->add('typeRec')
            ->add('useIdUsr')
            ->add('idUsr')
        ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'ReclamationBundle\Entity\Reclamation',
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getName()
    {
        return 'reclamation';
    }
}
