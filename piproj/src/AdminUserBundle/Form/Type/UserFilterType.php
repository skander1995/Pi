<?php

namespace AdminUserBundle\Form\Type;

use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\DateFilterType;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\TextFilterType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class UserFilterType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom', TextFilterType::class)
            ->add('prenom', TextFilterType::class)
            ->add('email', TextFilterType::class)
            ->add('datenaissance', DateFilterType::class)
            ->add('telephone', TextFilterType::class)
            ->add('photo_profile', TextFilterType::class)
            ->add('profilePicture', TextFilterType::class)
            ->add('createdAt', DateFilterType::class)
            //->add('updateAt', DateFilterType::class)
        ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'AdminUserBundle\Entity\User',
            'csrf_protection' => false,
            'validation_groups' => array('filter'),
            'method' => 'GET',
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getName()
    {
        return 'user_filter';
    }
}
