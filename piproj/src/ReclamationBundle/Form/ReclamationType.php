<?php

namespace ReclamationBundle\Form;

use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
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
            ->add('typeRec', ChoiceType::class, array(
                'choices' => array(
                    'Perte' => 'perte',
                    'Site Web' => 'Site Web',
                    'Utilisateur' => 'Utilisateur',
                )))
            ->add('useIdUsr', EntityType::class, array(
                'class' => 'AdminUserBundle:User',
                'choice_label' => 'nom',
                'multiple' => false,
                'data' => null,
                'empty_data' => null,
                'required' => false,
                //'attr'       => 'hidden=true'
            ))
            ->add('sujetRec')
            ->add('description');

    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'ReclamationBundle\Entity\Reclamation'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'reclamationbundle_reclamation';
    }


}
