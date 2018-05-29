<?php

namespace EducBundle\Form\Type;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class AideType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
        
            ->add('datepub')
            ->add('description')
            ->add('document')
            ->add('section')
            ->add('matiere')
            ->add('idusr')
        ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'EducBundle\Entity\Aide',
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getName()
    {
        return 'aide';
    }
}
