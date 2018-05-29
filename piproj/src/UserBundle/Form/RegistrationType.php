<?php
/**
 * Created by PhpStorm.
 * User: cobwi
 * Date: 21/03/2018
 * Time: 13:14
 */

namespace UserBundle\Form;


use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;

class RegistrationType extends AbstractType
{

    public function buildForm(FormBuilderInterface $builder, array $options)

    {
        $builder->add('nom', null, array(
                'label' => 'Nom',
                'translation_domain' => 'FOSUserBundle',
                'attr' => array('class' => 'form-control input-group-lg'))
        );
        $builder->add('prenom', null, array(
                'label' => 'Prenom',
                'translation_domain' => 'FOSUserBundle',
                'attr' => array('class' => 'form-control input-group-lg'))
        );
        $builder->add('datenaissance', null, array(
                'label' => 'Date de naissance',
                'translation_domain' => 'FOSUserBundle',
                //'attr' => array('class' => 'form-control')
            )
        );
    }

    public function getParent()
    {
        return 'FOS\UserBundle\Form\Type\RegistrationFormType';
    }

    public function getBlockPrefix()

    {
        return 'app_user_registration';
    }

    public function getName()

    {
        return $this->getBlockPrefix();
    }

}