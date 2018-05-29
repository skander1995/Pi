<?php

namespace CovoiturageBundle\Form\Type;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CovoiturageType extends AbstractType
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
            ->add('lieudepart')
            ->add('lieuarrive')
            ->add('datedepart')
            ->add('prix')
            ->add('checkpoints')
            ->add('nbplace')
            ->add('idVilleArr')
            ->add('vilVille')
            ->add('idVille')
        ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'CovoiturageBundle\Entity\Covoiturage',
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getName()
    {
        return 'covoiturage';
    }
}
