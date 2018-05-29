<?php

namespace AdminUserBundle\Form;

use FOS\UserBundle\Util\LegacyFormHelper;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;

class ResettingFormType extends AbstractType
{
    public function buildForm(FormBuilderInterface  $builder, array $options)
    {
        $builder->add('plainPassword', LegacyFormHelper::getType('Symfony\Component\Form\Extension\Core\Type\RepeatedType'), array(
            'type' => LegacyFormHelper::getType('Symfony\Component\Form\Extension\Core\Type\PasswordType'),
            'options' => array('translation_domain' => 'FOSUserBundle', 'attr' => array('class' => 'form-control')),
            'first_options' => array('label' => 'form.new_password'),
            'second_options' => array('label' => 'form.new_password_confirmation'),
            'invalid_message' => 'fos_user.password.mismatch',
        ));
    }
     public function getParent()
    {
        return 'FOS\FOSUserBundle\Form\Type\ResettingFormType';
    }

    public function getBlockPrefix()
    {
        return '_user_resetting';
    }

    public function getName()
    {
        return $this->getBlockPrefix();
    }

}
