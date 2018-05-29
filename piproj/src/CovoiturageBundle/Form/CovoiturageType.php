<?php

namespace CovoiturageBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\DateType;


class CovoiturageType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder//->add('datepub', null, array(
                //'attr' => array('class' => 'form-control')
           // ))


            ->add('description', null, array(
                'attr' => array('class' => 'wrap-input2 validate-input')
            ))
        // ->add('etat', null, array(
             //   'attr' => array('class' => 'wrap-input2 validate-input')
           // ))


            ->add('lieudepart', ChoiceType::class, array(
                'choices'=>array(

                    'Ariana'=>'Ariana', 'Béja'=>'Béja',
                    'Ben Arous'=> 'Ben Arous','Bizerte'=>'Bizerte',
                    'Gabès'=>'Gabès', 'Gafsa'=>'Gafsa',
                    'Jendouba'=>'Jendouba','Kairouan'=>'Kairouan',
                    'Kasserine'=>'Kasserine','Kébili'=>'Kébili',
                    'Le Kef'=>'Le Kef','Mahdia'=>'Mahdia',
                    'La Manouba'=>'La Manouba','Médenine'=>'Médenine',
                    'Monastir'=>'Monastir','Nabeul'=>'Nabeul',
                    'Sfax'=> 'Sfax','Sidi Bouzid'=>'Sidi Bouzid',
                    'Siliana'=>'Siliana','Sousse'=>'Sousse',
                    'Tataouine'=>'Tataouine','Tozeur'=>'Tozeur',
                    'Tunis'=>'Tunis','Zaghouan'=>'Zaghouan',),
                'attr' => array('class' => 'wrap-input2 validate-input')
            ))
            ->add('lieuarrive', ChoiceType::class, array(
                'choices'=>array(
                    'Ariana'=>'Ariana', 'Béja'=>'Béja',
                    'Ben Arous'=> 'Ben Arous','Bizerte'=>'Bizerte',
                    'Gabès'=>'Gabès', 'Gafsa'=>'Gafsa',
                    'Jendouba'=>'Jendouba','Kairouan'=>'Kairouan',
                    'Kasserine'=>'Kasserine','Kébili'=>'Kébili',
                    'Le Kef'=>'Le Kef','Mahdia'=>'Mahdia',
                    'La Manouba'=>'La Manouba','Médenine'=>'Médenine',
                    'Monastir'=>'Monastir','Nabeul'=>'Nabeul',
                    'Sfax'=> 'Sfax','Sidi Bouzid'=>'Sidi Bouzid',
                    'Siliana'=>'Siliana','Sousse'=>'Sousse',
                    'Tataouine'=>'Tataouine','Tozeur'=>'Tozeur',
                    'Tunis'=>'Tunis','Zaghouan'=>'Zaghouan',),
                'attr' => array('class' => 'wrap-input2 validate-input')
            ))

            ->add('datedepart', DateType::class, [
                'widget' => 'single_text'
            ])

            ->add('prix', null, array(
                'attr' => array('class' => 'wrap-input2 validate-input')
            ))
            //->add('checkpoints', null, array(
               // 'attr' => array('class' => 'wrap-input2 validate-input')
           // ))
            ->add('nbplace', null, array(
                'attr' => array('class' => 'wrap-input2 validate-input')
            ))


           ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'CovoiturageBundle\Entity\Covoiturage'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'covoituragebundle_covoiturage';
    }


}
