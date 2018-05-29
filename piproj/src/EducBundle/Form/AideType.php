<?php

namespace EducBundle\Form;

use Doctrine\DBAL\Types\DateType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\Date;

class AideType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            //->add('idusr',IntegerType::class,array('disabled'=>true))

            ->add('description', TextareaType::class , array(
                    'label' => 'Description',
                    'attr' => array('style' => 'width: 500px ; height : 100px; border-radius: 30px;
                             color: black;')
                )
            )
            ->add('document',FileType::class, array('label'=>'document'))

            ->add('section',TextType::class, array(
                'label' => 'Section',
                'attr' => array('style' => 'width: 500px ; border-radius: 30px;color: black;')
            ))
            ->add('matiere', TextType::class, array(
                'label' => 'Matiere' ,
                'attr' => array('style' => 'width: 500px ; border-radius: 30px;color: black;')
            ))
            ->add('Valider',SubmitType::class,array('attr' => array('style' => 'width: 50px; height:50px ')))
        ;
    }/**
 * {@inheritdoc}
 */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'EducBundle\Entity\Aide'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'educbundle_aide';
    }


}
