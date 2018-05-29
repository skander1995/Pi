<?php

namespace CovoiturageBundle\Form\Type;

use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\DateFilterType;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\EntityFilterType;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\NumberFilterType;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\TextFilterType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CovoiturageFilterType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
        
            ->add('idUsr', NumberFilterType::class)
            ->add('datepub', DateFilterType::class)
            ->add('description', TextFilterType::class)
            ->add('etat', TextFilterType::class)
            ->add('lieudepart', TextFilterType::class)
            ->add('lieuarrive', TextFilterType::class)
            ->add('datedepart', DateFilterType::class)
            ->add('prix', TextFilterType::class)
            ->add('checkpoints', TextFilterType::class)
            ->add('nbplace', NumberFilterType::class)
            ->add('idVilleArr', EntityFilterType::class, array('class' => 'TmpBundle\Entity\Ville'))
            ->add('vilVille', EntityFilterType::class, array('class' => 'TmpBundle\Entity\Ville'))
            ->add('idVille', EntityFilterType::class, array('class' => 'TmpBundle\Entity\Ville'))
        ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class'        => 'CovoiturageBundle\Entity\Covoiturage',
            'csrf_protection'   => false,
            'validation_groups' => array('filter'),
            'method'            => 'GET',
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getName()
    {
        return 'covoiturage_filter';
    }
}
