<?php

namespace AdminUserBundle\Form\Type;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Vich\UploaderBundle\Form\Type\VichImageType;

class UserType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
        ->add('id',HiddenType::class)
            ->add('enabled')
            ->add('username', null, array('label' => 'form.username', 'translation_domain' => 'FOSUserBundle'))
            ->add('password')
            ->add('email')
            ->add('nom')
            ->add('prenom')
            ->add('datenaissance')
            ->add('telephone')
            //->add('photo_profile')
            //->add('profilePicture')
            ->add('profile_picture_file', VichImageType::class, array(
                    'required' => false,
                    'label' => 'Profile Picture'
                )
            )
            ;
            /*
            ->add('createdAt', DateType::class, array(
                'widget' => 'single_text',
            ))
            ->add('updateAt', DateType::class, array(
                'widget' => 'single_text',
            ));
            */
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'AdminUserBundle\Entity\User',
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getName()
    {
        return 'user';
    }
}
