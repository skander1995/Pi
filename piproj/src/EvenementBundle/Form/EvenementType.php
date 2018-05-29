<?php

namespace EvenementBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class EvenementType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('nomEvent', null, array(
                'label' => 'Nom',
                'translation_domain' => 'EvenementBundle',
                'required'   => true ,
                'attr' => array(
                    'class' => 'form-control input-group-lg',
                    'placeholder' => 'Le nom de l\'évenement'
                )
        ));
        $builder->add('description', null, array(
            'label' => 'Description',
            'translation_domain' => 'EvenementBundle',
            'required'   => true ,
            'attr' => array(
                'class' => 'form-control input-group-lg',
                'placeholder' => 'Descrription de l\'évenement'
            )
        ));

        $builder->add('datedebut', null, array(

                'label' => 'Date de début',
                'translation_domain' => 'EvenementBundle',
                'required'   => true ,
                //'attr' => array('class' => 'form-control')
            )
        );

        $builder->add('datefin', null, array(

                'label' => 'Date de fin',
                'translation_domain' => 'EvenementBundle',
                'required'   => true ,
                //'attr' => array('class' => 'form-control')
            )
        );

        $builder->add('affiche' , FileType::class,array('label'=>'insrer une image' , 'data_class' => null ));

        $builder->add('lieu', null, array(
            'label' => 'Lieu',
            'translation_domain' => 'EvenementBundle',
            'required'   => true ,

            'attr' => array(
                'class' => 'form-control input-group-lg',
                'placeholder' => 'Lieu de l\'évenement'

            )
        ));

        $builder->add('placeDispo', null, array(
            'label' => 'Nombre des places disponibles',
            'translation_domain' => 'EvenementBundle',
            'required'   => true ,
            'attr' => array(
                'class' => 'form-control input-group-lg',
                'placeholder' => 'Places'
            )
        ));

    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'EvenementBundle\Entity\Evenement'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'evenementbundle_evenement';
    }


}
