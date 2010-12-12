class Membership < ActiveRecord::Base
  belongs_to :contact, :class_name => 'User'
  belongs_to :group
  validate :check_for_duplicates

  protected
  def check_for_duplicates
    puts group.contacts.methods.sort
    errors.add(:contact, 'Contact is already a member of group') if self.group.contacts.include?(contact)
  end
end