<?php

namespace EvenementBundle\Form\Type;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class EvenementType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
        
            ->add('idUsr')
            ->add('datepub')
            ->add('description')
            ->add('etat')
            ->add('nomEvent')
            ->add('datedebut')
            ->add('datefin')
            ->add('lieu')
            ->add('affiche')
            ->add('placeDispo')
            ->add('idVilleS')
        ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'EvenementBundle\Entity\Evenement',
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getName()
    {
        return 'evenement';
    }
}
